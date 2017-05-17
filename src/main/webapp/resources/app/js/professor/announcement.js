/**
 * Created by Calvin on 4/16/2017.
 */
var app = angular.module('homeApp');
var initLoad = true;
/* TIME FORMATTING OPTIONS */
var options = {
    weekday: "long", year: "numeric", month: "short",
    day: "numeric", hour: "2-digit", minute: "2-digit"
};
/* INIT QUILL EDITOR */
var addQuill;

/* FACTORY TO HANDLE HTTP REQUEST LOGIC */
app.factory('httpAnnouncementFactory', function($http, global) {
    /* SET SINGLETON LIKE OBJECT */
    var properties = this;
    /* getAnnouncements- RETURNS ARRAY OF COURSE's ANNOUNCEMENTS */
    properties.getAnnouncements = function(){
        return $http.get("/getAnnouncement", {
            params : {
                "courseId" : global.getCourseId()
            }
        });
    };

    /* addAnnouncement - CREATES A NEW ANNOUNCEMENT AND RETURNS NEWLY MADE ANNOUNCEMENT W/ ID */
    properties.addAnnouncement = function(){
        /* VALIDATION */
        var valid = true;
        if( ($('#addAnnouncementTitle').val() == "")){
            $('#addAnnouncementTitleEmpty').show();
            valid = false;
        }
        if( $('#addAnnouncementTitle').val().length > 30 ){
            $('#addAnnoucnementTitleLength').show();
            valid = false;
        }
        if(addQuill.getText().trim().length == 0){
            $('#addAnnouncementQuillError').show();
            valid = false;
        }
        /* CHECK IF VALID */
        if(valid === false){
            return "invalid";
        }
        $('#addAnnouncementTitleEmpty').hide();
        $('#addAnnoucnementTitleLength').hide();
        $('#addAnnouncementQuillError').hide();
        /* RETURN NEWLY MADE ANNOUNCEMENT */
        return $http({
            method: 'GET',
            url: '/updateAnnouncement',
            params: {"courseId" : global.getCourseId(), "title": $('#addAnnouncementTitle').val(), "description" : JSON.stringify(addQuill.getContents())}
        });
    };

    /* updateAnnouncement - UPDATES AN ASSIGNMENT WITH EDITS MADE BY USER */
    properties.updateAnnouncement = function(announcement, index) {
        var id = "#announcementDescription-"+index;
        var title = "#announcementTitle-"+index;
        var emptyError = "#announcementTitleEmpty-"+index;
        var lengthError = "#announcementTitleLength-"+index;
        var quillError = "#announcementQuillError-"+index;
        /* SET VALIDATION */
        var valid = true;
        /* IF THE TITLE IS EMPTY, SHOW EMPTY TITLE ERROR */
        if( ($(title).val() == "")){
            $(emptyError).show();
            valid = false;
        }
        /* IF TITLE IS TOO LONG, SHOW ERROR */
        if( $(title).val().length > 30 ){
            $(lengthError).show();
            valid = false;
        }
        /* IF DESCRIPTION IS EMPTY, SHOW ERROR */
        if(announcement.quill.getText().trim().length == 0){
            $(quillError).show();
            valid = false;
        }
        /* CHECK THAT CHANGES WERE VALID */
        if(valid === false){
            return "invalid";
        }
        /* HIDE ALL ERRORS IF OK */
        $(emptyError).hide();
        $(lengthError).hide();
        $(quillError).hide();
        var params = {
            "id" : announcement.id,
            "title": $(title).val(),
            "description" : JSON.stringify(announcement.quill.getContents())
        };
        return $http({
            method: 'GET',
            url: '/updateAnnouncement',
            params: params
        });
    };

    /* deleteAssignment - deletes an assigment and any attached files previously uploaded */
    properties.deleteAnnouncement = function(id){
        return $http({
            method: 'GET',
            url: '/deleteAnnouncement',
            params: {"id": id}
        });
    };

    return properties;
});

/* ANNOUNCEMENT CONTROLLER */
app.controller('announcementsCtrl', function ($scope, $http, $state, global, httpAnnouncementFactory) {

    $scope.$watch(function(){
        return global.getCourseId();
    }, function(newValue, oldValue){
        /* check if courseId has really changed */
        if(newValue !== undefined && newValue !== 0 && newValue !== oldValue){
            reloadData();
        }
    });
    var reloadData = function(){
        $state.reload();
    };

    addQuill = new Quill('#editor', {
        placeholder: 'Announcement Description',
        theme: 'snow'

    });
    /* INIT ANNOUNCEMENT LIST */
    $scope.announcementList = [];
    /* LOAD ANNOUNCEMENTS FOR COURSE */
    httpAnnouncementFactory.getAnnouncements().success(function(response){
        /* FORMAT DUE DATE OF EACH ANNOUNCEMENT */
        $.each(response, function() {
            this.dateCreated = new Date(this.dateCreated).toLocaleTimeString("en-us", options);
            /* ADD ANNOUNCEMENT TO LIST */
            $scope.announcementList.unshift(this);
        });
        initLoad = true;
    }).error(function(response){
    });

    /* editAnnouncement - CHANGE UI FOR EDITING */
    $scope.editAnnouncement = function(announcement,index){

        var id = "#announcementDescription-"+index;
        /* SHOWS THE ANNOUNCEMENT TOOLBAR, EDITOR BORDER, AND ENABLES EDITING */
        $(id).prev().show();

        var buttonId = "#updateButton-"+index;
        var cancelId = '#cancelEdit-'+index;

        $(buttonId).show();
        $(cancelId).show();

        $(id).css({'border': '1px solid #ccc','border-top' :' 0px'});
        announcement.quill.enable(true);

        var announcementTitle = "#announcementTitle-"+index;
        $(announcementTitle).removeAttr("disabled");
        $(announcementTitle).css('border','1px solid black');
    };

    /* UPDATE THE ANNOUNCEMENT WITH CHANGES MADE */
    $scope.updateAnnouncement = function(announcement,index){
        httpAnnouncementFactory.updateAnnouncement(announcement,index).success(function (response) {
            /* CHECK FOR IMPROPER UPDATE DATA */
            if(response === "invalid"){
                return;
            }
            /* HIDE THE ANNOUNCEMENT TOOLBARS */
            var id = "#announcementDescription-"+index;
            $(id).prev().hide();
            /* HIDE THE BUTTONS */
            var buttonId = "#updateButton-"+index;
            var cancelId = '#cancelEdit-'+index;
            $(buttonId).hide();
            $(cancelId).hide();

            /* HIDE THE BORDERS */
            $(id).css({'border': 'none'});
            /* DISABLE QUILL EDITOR */
            announcement.quill.enable(false);
            var announcementTitle = "#announcementTitle-"+index;
            $(announcementTitle).attr("disabled", "disabled");
            $(announcementTitle).css('border','none');

            /* SAVE THE DATA OF THE TITLE AND QUILL */
            announcement.title = $(announcementTitle).val();
            announcement.description = announcement.quill.getContents();

        }).error(function(response){
            console.log(response);
        });

    };

    /* CANCEL ANY EDITS MADE BY THE USER */
    $scope.cancelEdit = function(announcement,index){
        var id = "#announcementDescription-"+index;
        var emptyError = "#announcementTitleEmpty-"+index;
        var lengthError = "#announcementTitleLength-"+index;
        var quillError = "#announcementQuillError-"+index;
        /* HIDE THE EDIT OPTIONS */
        $(id).prev().hide();
        var buttonId = "#updateButton-"+index;
        var cancelId = '#cancelEdit-'+index;
        $(buttonId).hide();
        $(cancelId).hide();

        /* HIDE ANY ERRORS MADE FROM EDIT MODE */
        $(emptyError).hide();
        $(lengthError).hide();
        $(quillError).hide();

        /* REMOVE BORDER AND DISABLE EDITOR */
        $(id).css({'border': 'none'});
        announcement.quill.enable(false);
        var announcementTitle = "#announcementTitle-"+index;
        $(announcementTitle).attr("disabled", "disabled");
        $(announcementTitle).css('border','none');

        /* RELOAD PREV DATA OF TITLE AND QUILL */
        /* DID NOT USE ng-model FOR TITLE SO WE SAVE THE TITLE IF THEY SELECT 'CANCEL' */
        var titleId = "#announcementTitle-"+index;
        $(titleId).val(announcement.title);
        announcement.quill.setContents(JSON.parse(announcement.description));
    };

    /* CREATE A NEW ANNOUNCEMENT */
    $scope.addAnnouncement = function(){
        httpAnnouncementFactory.addAnnouncement().success(function (response) {
            /* CHECK THAT RESPONSE IS VALID */
            if(response === "invalid"){
                return;
            }
            if(response !== "") {
                $scope.announcementList.push(response);
                /* CLEAR THE FORM */
                $("#addAnnouncementForm")[0].reset();
                /* CLEAR addQuill */
                addQuill.clipboard.dangerouslyPasteHTML("");
            }else{
                /* SOMETHING WENT WRONG */
                console.log("Empty Response");
            }
        }).error(function(response){
            console.log(response);
        });
    };

    /*DELETES AN ANNOUNCEMENT*/
    $scope.deleteAnnouncement = function(announcement, index){
        httpAnnouncementFactory.deleteAnnouncement(announcement.id).success(function (response) {
            if(response === true) {
                /* REMOVE DELETED ANNOUNCEMENT FROM MODEL */
                $scope.announcementList.splice(index,1);
            }
        }).error(function(response){
            console.log(response);
        });
    };
});

/* DIRECTIVE TO INITIATE ALL THE QUILLS AS THEY LOAD */
app.directive('testdirective', function() {
    return function(scope, element, attrs) {
        scope.$watch('$last',function(v){
            if (v === true) {
                if((initLoad)){
                    initLoad = false;
                    for(var i = 0; i < scope.announcementList.length; i++) {
                        var id = "#announcementDescription-" + i;
                        var loadQuill = new Quill(id, {
                            placeholder: 'Announcement Description',
                            theme: 'snow'
                        });
                        if(scope.announcementList.length === 1){
                            scope.announcementList[0].dateCreated = new Date(scope.announcementList[0].dateCreated).toLocaleTimeString("en-us", options);

                        }
                        /* DISABLE THE QUILL */
                        loadQuill.enable(false);

                        loadQuill.setContents(JSON.parse(scope.announcementList[i].description));

                        scope.announcementList[i].quill = loadQuill;

                    }
                    /* HIDE THE ANNOUNCEMENT TOOLBARS */
                    $(".annnouncementEditors").prev().hide();

                }else{
                    /* ONLY DO STUFF TO THE LAST ANNOUNCEMENT
                     * FIRST CHECK IF IT'S A NEW THING OR JUST A DELETE
                     * CHECK IF HAS TOOLBAR */
                    var id = "#announcementDescription-"+(scope.announcementList.length - 1);
                    if((!$(id).prev().hasClass("ql-toolbar"))){
                        var newAnnouncement = scope.announcementList[scope.announcementList.length - 1];
                        scope.announcementList.unshift(newAnnouncement);
                        debugger;
                        scope.announcementList.splice((scope.announcementList.length-1),1);
                        debugger;
                        /* INIT THE QUILL */
                        var loadQuill = new Quill(id, {
                            placeholder: 'Announcement Description',
                            theme: 'snow'
                        });
                        loadQuill.setContents(JSON.parse(scope.announcementList[0].description));
                        /* HIDE THE ANNOUNCEMENT TOOLBAR */
                        $(id).prev().hide();

                        /* HIDE THE BUTTON */
                        var buttonId = "#updateButton-0";
                        var cancelId = '#cancelEdit-0';

                        $(buttonId).hide();
                        $(cancelId).hide();

                        /* HIDE THE BORDERS */
                        $(id).css({'border': 'none'});
                        /* DISABLE THE QUILL */
                        loadQuill.enable(false);

                        var announcementTitle = "#announcementTitle-0";
                        $(announcementTitle).attr("disabled", "disabled");

                        scope.announcementList[0].dateCreated = new Date(scope.announcementList[0].dateCreated).toLocaleTimeString("en-us", options);

                        scope.announcementList[0].quill = loadQuill;
                    }
                }
            }else{
            }
        });
    };
});
