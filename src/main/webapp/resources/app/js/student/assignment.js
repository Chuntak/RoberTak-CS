/**
 * Created by Chuntak on 4/21/2017.
 */
/* GET THE MODULE FOR ANGULAR FUNCTIONS */
var app = angular.module('homeApp');
/* DIRECTIVE FOR FILES */
app.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

/* FACTORY TO HANDLE HTTP REQUEST LOGIC */
app.factory('httpAssignmentFactory', function($http, global) {
    /* SET SINGLETON LIKE OBJECT */
    var properties = this;
    /* getAssignments - GET THE ASSIGNMENTS FOR THE COURSE */
    properties.getAssignments = function(){
        return $http.get("/getAssignments", {
            params : {
                "crsId" : global.getCourseId(),
                "gradableType" : "hw"
            }
        });
    };
    /* updateSubmission - UPLOADS STUDENT SUBMISSION */
    properties.uploadSubmission= function(asgmt) {
        /* CREATE PARAMETERS FOR HTTP REQUEST */
        var params = {
            "id": asgmt.id,
            "submissionBlobName":asgmt.submissionBlobName
        };
        /* MAKE THE FILE STUFF */
        if (asgmt.submission) {
            var fd = new FormData();
            fd.append('hwFile', asgmt.submission);
            fd.append('f', 'json');
            /* RETURN THE HTTP REQUEST TO ANGULAR CONTROLLER */
            return $http.post("/uploadSubmission", fd, {
                transformRequest: angular.identity,
                headers: {
                    'Content-Type': undefined
                },
                params: params
            });
        }
    };
    return properties;
});

/* CONTROLLER FOR ASSIGNMENTS TAB */
app.controller('assignmentsCtrl', function ($scope, $http, global, httpAssignmentFactory) {

    /* LIST OF ASSIGNMENTS */
    $scope.assignments = [];
    /* SELECTED ASSIGNMENT */
    $scope.assignment = {};

    /* TIME FORMATTING OPTIONS */
    var options = {
        weekday: "long", year: "numeric", month: "short",
        day: "numeric", hour: "2-digit", minute: "2-digit"
    };

    /* GET ASSIGNMENTS FOR COURSE */
    httpAssignmentFactory.getAssignments().success(function(response){
        /* FORMAT DUE DATE */
        $.each(response, function() {
            this.dueDate = new Date(this.dueDate).toLocaleTimeString("en-us", options);
        });
        $scope.assignments = response;
    }).error(function(response){
        console.log(response);
    });

    /* uploadSubmission - UPLOADS STUDENT SUBMISSION */
    $scope.uploadSubmission = function(asgmt){
        httpAssignmentFactory.uploadSubmission(asgmt).success(function(response){
            console.log(response);
        }).error(function(response){
            console.log(response);
        });
    }
});

