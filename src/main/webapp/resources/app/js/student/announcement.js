
/**
 * Created by Calvin on 4/21/2017.
 */
var app = angular.module('homeApp');
var initLoad = true;

/* TIME FORMATTING OPTIONS */
var options = {
    weekday: "long", year: "numeric", month: "short",
    day: "numeric", hour: "2-digit", minute: "2-digit"
};

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

    return properties;
});

/* ANNOUNCEMENT CONTROLLER */
app.controller('announcementsCtrl', function ($scope, $http, $state, global, httpAnnouncementFactory) {

    /* INIT ANNOUNCEMENT LIST */
    $scope.announcementList = [];

    /* GET ANNOUNCEMENTS FOR COURSE ON LOAD */
    httpAnnouncementFactory.getAnnouncements().success(function(response){
        initLoad = true;
        /* FORMAT DUE DATE OF EACH ANNOUNCEMENT */
        $.each(response, function() {
            this.dateCreated = new Date(this.dateCreated).toLocaleTimeString("en-us", options);
            /* ADD ANNOUNCEMENT TO LIST */
            $scope.announcementList.unshift(this);
        });
    }).error(function(response){
        console.log(response);
    });
});

/* DIRECTIVE TO INIT ALL THE QUILLS AS THEY LOAD */
app.directive('testdirective', function() {
    return function(scope, element, attrs) {
        scope.$watch('$last',function(v){
            if (v == true) {
                if((initLoad)){
                    initLoad = false;
                    for(var i = 0; i < scope.announcementList.length; i++) {
                        var id = "#announcementDescription-" + i;
                        var loadQuill = new Quill(id, {
                            placeholder: 'Announcement Description',
                            theme: 'snow'
                        });

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
                    var id = "#announcementDescription-" + (scope.announcementList.length - 1);
                    if((!$(id).prev().hasClass("ql-toolbar"))){
                        /* INIT THE QUILL */
                        var loadQuill = new Quill(id, {
                            placeholder: 'Announcement Description',
                            theme: 'snow'
                        });
                        loadQuill.setContents(JSON.parse(scope.announcementList[scope.announcementList.length - 1].description));
                        /* HIDE THE ANNOUNCEMENT TOOLBARS */
                        $(id).prev().hide();

                        /* HIDE THE BUTTONS */
                        var buttonId = "#updateButton-"+(scope.announcementList.length - 1);
                        var cancelId = '#cancelEdit-'+(scope.announcementList.length - 1);

                        $(buttonId).hide();
                        $(cancelId).hide();

                        /* HIDE THE BORDERS */
                        $(id).css({'border': 'none'});
                        /* DISABLE THE QUILL */
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
