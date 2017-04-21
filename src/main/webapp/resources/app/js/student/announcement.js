/**
 * Created by Calvin on 4/21/2017.
 */

var app = angular.module('homeApp');
var initLoad = true;
/*Announcement Controller*/
app.controller('announcementsCtrl', function ($scope, $http, global) {
    $scope.global = global;
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
                    $(".annnouncementEditors").prev().remove();

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
                        $(id).prev().remove();

                        //Disable the quill
                        loadQuill.enable(false);
                    }
                    debugger;
                }
            }
        });
    };
})
