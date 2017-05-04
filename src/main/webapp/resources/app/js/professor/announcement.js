/**
 * Created by Calvin on 4/16/2017.
 */


var app = angular.module('homeApp');
var initLoad = true;
/*Announcement Controller*/
app.controller('announcementsCtrl', function ($scope, $http, $state, global) {

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
    //Pre-emtively hide the add announcement pullout
    $('#addAnnouncementDiv').hide();
    //Initialize Quill editor
    var addQuill = new Quill('#editor', {
        placeholder: 'Announcement Description',
        theme: 'snow'

    });

    $scope.announcementList = {};

    //Get Announcements on load

    $http.get("/getAnnouncement", {
        params : {
            "courseId" : global.getCourseId()
        }
    }).success(function(response){
        initLoad = true;
        $scope.announcementList = response;
    }).error(function(response){
    });




    //Clicks on edit button.
    $scope.editAnnouncement = function(announcement,index){

        var id = "#announcementDescription-"+index;
        //Shows the announcement toolbar, editor border, and enables editing
        $(id).prev().show();

        var buttonId = "#updateButton-"+index;
        var cancelId = '#cancelEdit-'+index;

        $(buttonId).show();
        $(cancelId).show();

        $(id).css({'border': '1px solid #ccc','border-top' :' 0px'});
        announcement.quill.enable(true);

        var announcementTitle = "#announcementTitle-"+index;
        $(announcementTitle).removeAttr("disabled");
    };




    $scope.updateAnnouncement = function(announcement,index){
        var id = "#announcementDescription-"+index;
        var title = "#announcementTitle-"+index;
        var emptyError = "#announcementTitleEmpty-"+index;
        var lengthError = "#announcementTitleLength-"+index;
        var quillError = "#announcementQuillError-"+index;
        //Validation
        var valid = true;
        //If the title is empty, show empty title error
        if( ($(title).val() == "")){
            $(emptyError).show();
            valid = false;
        }
        //If the announcement Title is too long, show title too long error
        if( $(title).val().length > 30 ){
            $(lengthError).show();
            valid = false;
        }
        //If the Announcement Description is empty, show the empty quill error
        if(announcement.quill.getText().trim().length == 0){
            $(quillError).show();
            valid = false;
        }
        if(valid === false){
            return;
        }
        $(emptyError).hide();
        $(lengthError).hide();
        $(quillError).hide();

        var y = $http({
            method: 'GET',
            url: '/updateAnnouncement',
            params: {"id" : announcement.id, "title": $(title).val(), "description" : JSON.stringify(announcement.quill.getContents())}
        }).then(function (response) {
            console.log(response)
            //Hide the announcement toolbars
            $(id).prev().hide();
            //Hide the button
            var buttonId = "#updateButton-"+index;
            var cancelId = '#cancelEdit-'+index;

            $(buttonId).hide();
            $(cancelId).hide();

            //Hide the borders
            $(id).css({'border': 'none'});
            //Disable the quill
            announcement.quill.enable(false);

            var announcementTitle = "#announcementTitle-"+index;
            $(announcementTitle).attr("disabled", "disabled");

        }, function errorCallBack(response) {
            alert("Edit announcement error\n");
        });

    };

    $scope.cancelEdit = function(announcement,index){
        var id = "#announcementDescription-"+index;
        var emptyError = "#announcementTitleEmpty-"+index;
        var lengthError = "#announcementTitleLength-"+index;
        var quillError = "#announcementQuillError-"+index;
        //Hide the edit options
        $(id).prev().hide();
        var buttonId = "#updateButton-"+index;
        var cancelId = '#cancelEdit-'+index;

        $(buttonId).hide();
        $(cancelId).hide();

        //Hide any errors from Edit mode
        $(emptyError).hide();
        $(lengthError).hide();
        $(quillError).hide();


        $(id).css({'border': 'none'});
        announcement.quill.enable(false);

        var announcementTitle = "#announcementTitle-"+index;
        $(announcementTitle).attr("disabled", "disabled");

        //Reload the data of the title and quill
        //We did not use ng-model for title so we save the title if they select cancel edit
        var titleId = "#announcementTitle-"+index;
        $(titleId).val(announcement.title);


        announcement.quill.setContents(JSON.parse(announcement.description));
    };

    /*HIDES THE ADD ANNOUNCEMENT PULL OUT AND THE ERRORS */
    $scope.toggleAdd = function(){
        $('#addAnnouncementDiv').fadeToggle('fast');
        $('#addAnnouncementTitleEmpty').hide();
        $('#addAnnoucnementTitleLength').hide();
        $('#addAnnouncementQuillError').hide();
    };



    $scope.addAnnouncement = function(){
        /*Validation*/
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
        if(valid === false){
            return;
        }
        $('#addAnnouncementTitleEmpty').hide();
        $('#addAnnoucnementTitleLength').hide();
        $('#addAnnouncementQuillError').hide();

        var y = $http({
            method: 'GET',
            url: '/updateAnnouncement',
            params: {"courseId" : global.getCourseId(), "title": $('#addAnnouncementTitle').val(), "description" : JSON.stringify(addQuill.getContents())}
        }).then(function (response) {
            console.log(response);
            if(response.data !== "") {
                $scope.announcementList.push(response.data);
                //Clear the things
                $("#addAnnouncementForm")[0].reset();
                //Clears the add quill
                addQuill.clipboard.dangerouslyPasteHTML("");
                //Closes the add div
                $('#addAnnouncementDiv').fadeToggle('fast');
            }else{
                console.log(response)
            }
        }, function errorCallBack(response) {
            alert("add announcement error\n");
        });
    };

    /*DELETES AN ANNOUNCEMENT*/
    $scope.deleteAnnouncement = function(announcement){
        var y = $http({
            method: 'GET',
            url: '/deleteAnnouncement',
            params: {"id": announcement.id}
        }).then(function (response) {
            if(response.data === true) {  /*add*/
                for(var i = 0; i < $scope.announcementList.length; i++){
                    if($scope.announcementList[i].id === announcement.id){
                        $scope.announcementList.splice(i,1);
                        break;
                    }
                }
            }
        }, function errorCallBack(response) {
            alert("delete anncounement error\n");
        });
    };




});

//Directive to initiate all the quills as they load
app.directive('testdirective', function() {
    return function(scope, element, attrs) {
        scope.$watch('$last',function(v){
            if (v === true) {
                if((initLoad)){
                    initLoad = false;
                    for(var i = 0; i < scope.announcementList.length; i++) {
                        ;
                        var id = "#announcementDescription-" + i;
                        var loadQuill = new Quill(id, {
                            placeholder: 'Announcement Description',
                            theme: 'snow'
                        });

                        //Disable the quill
                        loadQuill.enable(false);

                        loadQuill.setContents(JSON.parse(scope.announcementList[i].description));

                        scope.announcementList[i].quill = loadQuill;

                    }
                    //Hide the announcement toolbars
                    $(".annnouncementEditors").prev().hide();

                }else{
                    //Only do stuff to the last announcement
                    //First check if it's a new thing or just a delete
                    //Check if has toolbar
                    var id = "#announcementDescription-" + (scope.announcementList.length - 1);
                    if((!$(id).prev().hasClass("ql-toolbar"))){
                        //Init the quill
                        var loadQuill = new Quill(id, {
                            placeholder: 'Announcement Description',
                            theme: 'snow'
                        });
                        loadQuill.setContents(JSON.parse(scope.announcementList[scope.announcementList.length - 1].description));
                        //Hide the announcement toolbars
                        $(id).prev().hide();

                        //Hide the button
                        var buttonId = "#updateButton-"+(scope.announcementList.length - 1);
                        var cancelId = '#cancelEdit-'+(scope.announcementList.length - 1);

                        $(buttonId).hide();
                        $(cancelId).hide();

                        //Hide the borders
                        $(id).css({'border': 'none'});
                        //Disable the quill
                        loadQuill.enable(false);

                        var announcementTitle = "#announcementTitle-"+(scope.announcementList.length - 1);
                        $(announcementTitle).attr("disabled", "disabled");

                        scope.announcementList[scope.announcementList.length - 1].quill = loadQuill;
                    }
                }
            }else{
            }
        });


    };
});
