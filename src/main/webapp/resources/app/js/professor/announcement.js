/**
 * Created by Calvin on 4/16/2017.
 */


var app = angular.module('homeApp');
var initLoad = true;
/*Announcement Controller*/
app.controller('announcementsCtrl', function ($scope, $http, global) {
    $scope.global = global;
    <!-- Initialize Quill editor -->

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
        debugger;
        initLoad = true;
        $('#addAnnouncementDiv').fadeToggle('fast');
        $scope.announcementList = response;
    }).error(function(response){
        debugger;
    });



    //Clicks on edit button.
    $scope.editAnnouncement = function(announcement,index){
        debugger;
        var id = "#announcementDescription-"+index;
        var loadQuill = new Quill(id, {
        });
        //Shows the announcement toolbar, editor border, and enables editing
        $(id).prev().show();

        var buttonId = "#updateButton-"+index;
        var cancelId = '#cancelEdit-'+index;

        $(buttonId).show();
        $(cancelId).show();

        $(id).css({'border': '1px solid #ccc','border-top' :' 0px'});
        loadQuill.enable(true);
    }




    $scope.updateAnnouncement = function(announcement,index){
        debugger;
        var id = "#announcementDescription-"+index;
        var loadQuill = new Quill(id, {});
        var title = "#announcementTitle-"+index;
        var y = $http({
            method: 'GET',
            url: '/updateAnnouncement',
            params: {"id" : announcement.id, "title": $(title).val(), "description" : JSON.stringify(loadQuill.getContents())}
        }).then(function (response) {
            console.log(response)
            debugger;
            if(response.data !== "") {
                $scope.announcementList.push(response.data)
                debugger;




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
                loadQuill.enable(false);


                //Go to Back End with the data
                var data = JSON.stringify(loadQuill.getContents());
                //Update the Title and Quill
            }else{
                console.log(response)
                debugger;
            }
        }, function errorCallBack(response) {
            debugger;
            alert("Edit announcement error\n");
        });

    }

    $scope.cancelEdit = function(announcement,index){
        var id = "#announcementDescription-"+index;
        var loadQuill = new Quill(id, {
        });
        //Hide the edit options
        $(id).prev().hide();
        var buttonId = "#updateButton-"+index;
        var cancelId = '#cancelEdit-'+index;

        $(buttonId).hide();
        $(cancelId).hide();

        $(id).css({'border': 'none'});
        loadQuill.enable(false);
        //Reload the data of the title and quill
        //We did not use ng-model for title so we save the title if they select cancel edit
        var titleId = "#announcementTitle-"+index;
        $(titleId).val(announcement.title);


        loadQuill.setContents(JSON.parse(announcement.description));
        //TODO USE DELTA OBJECTS
    }

    $scope.toggleAdd = function(){
        $('#addAnnouncementDiv').fadeToggle('fast');

    }



    $scope.addAnnouncement = function(){
        debugger;
        var y = $http({
            method: 'GET',
            url: '/updateAnnouncement',
            params: {"courseId" : global.getCourseId(), "title": $('#addAnnouncementTitle').val(), "description" : JSON.stringify(addQuill.getContents())}
        }).then(function (response) {
            console.log(response)
            debugger;
            if(response.data !== "") {
                $scope.announcementList.push(response.data)
                //Clear the things
                $("#addAnnouncementForm")[0].reset();
                //Clears the add quill
                addQuill.clipboard.dangerouslyPasteHTML("");
            }else{
                console.log(response)
                debugger;
            }
        }, function errorCallBack(response) {
            debugger;
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
        debugger;
        scope.$watch('$last',function(v){
            if (v == true) {
                if((initLoad)){
                    initLoad = false;
                    debugger;
                    for(var i = 0; i < scope.announcementList.length; i++) {
                        debugger;
                        var id = "#announcementDescription-" + i;
                        var loadQuill = new Quill(id, {
                            placeholder: 'Announcement Description',
                            theme: 'snow'
                        });

                        //Disable the quill
                        loadQuill.enable(false);

                        loadQuill.setContents(JSON.parse(scope.announcementList[i].description));
                    }

                    //Hide the announcement toolbars
                    $(".annnouncementEditors").prev().hide();

                    //Hide the button
                    $(".updateAnnouncement").hide();
                    $(".cancelEdit").hide();

                    //Hide the borders
                    $(".annnouncementEditors.ql-container.ql-snow").css({'border': 'none'});

                }else{
                    //Only do stuff to the last announcement
                    //First check if it's a new thing or just a delete
                    //Check if has toolbar
                    var id = "#announcementDescription-" + (scope.announcementList.length - 1);
                    if((!$(id).prev().hasClass("ql-toolbar"))){
                        debugger;
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
                    }
                    debugger;
                }
            }else{
                debugger;
            }
        });


    };
})
