/**
 * Created by Calvin on 4/21/2017.
 */

var app = angular.module('homeApp');
var initLoad = true;
/*Announcement Controller*/
app.controller('announcementsCtrl', function ($scope, $http, $state, global) {

    $scope.$watch(function(){
        return global.getCourseId();
    }, function(newValue, oldValue){
        /* check if courseId has really changed */
        if(newValue !== undefined && newValue != 0 && newValue !== oldValue){
            reloadData();
        }
    });
    var reloadData = function(){
        $state.reload();
    }
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
        $scope.announcementList = response;
    }).error(function(response){
    });
});

//Directive to initiate all the quills as they load
app.directive('testdirective', function() {
    return function(scope, element, attrs) {
        scope.$watch('$last',function(v){
            if (v == true) {
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

                        announcement.quill.setContents(JSON.parse(announcement.description));
                    }

                    //Hide the borders
                    $(".annnouncementEditors.ql-container.ql-snow").css({'border': 'none'});

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

                        //Hide the borders
                        $(id).css({'border': 'none'});
                        //Disable the quill
                        loadQuill.enable(false);

                        scope.announcementList[scope.announcementList.length - 1].quill = loadQuill;
                    }
                }
            }else{
            }
        });


    };
})
