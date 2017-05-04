/**
 * Created by Chuntak on 4/21/2017.
 */
var app = angular.module('homeApp');

/* FACTORY TO HANDLE HTTP REQUEST LOGIC */
app.factory('httpFactory', function($http, global) {
    /* SET SINGLETON LIKE OBJECT */
    var properties = this;

    /* updateAssignment - adds or updates an assignment */
    properties.updateAssignment = function(newAsgmt) {
        /* CONVERT TIME TO PROPER FORMAT HH:MM:SS */
        var tmpDate = new Date("1/1/1111 " + newAsgmt.time);
        var time = tmpDate.getHours() + ":" + tmpDate.getMinutes() + ":" + "00";
        newAsgmt.time = time;
        /* CREATE PARAMETERS FOR HTTP REQUEST */
        var params = {
            "id" : newAsgmt.id,
            "title" : newAsgmt.title,
            "description" : newAsgmt.descr,
            "courseId" : global.getCourseId(),
            "gradableType" : "hw",
            "maxGrade" : newAsgmt.maxGrade,
            "dueDate" : new Date(newAsgmt.date + " " + time).getTime(),
            "difficulty" : "hard"
        };
        /* MAKE THE FILE STUFF */
        var fd = new FormData();
        fd.append('hwFile', newAsgmt.file);
        fd.append('f', 'json');
        /* RETURN THE HTTP REQUEST TO ANGULAR CONTROLLER */
        return $http.post("/updateAssignment", fd, {
            transformRequest : angular.identity,
            headers : {
                'Content-Type' : undefined
            },
            params : params
        });
    }


    return properties;
});

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

/* CONTROLLER FOR ASSIGNMENTS TAB */
app.controller('assignmentsCtrl', function ($scope, $http, global, httpFactory) {

    $scope.assignment = {};
    /* KEEP TRACK OF NEW ASSIGNMENT */
    $scope.newAsgmt = {};
    //$scope.assignment.hwFilesList = [{}];
    //$scope.hws = [];

    /* INIT THE DATEPICKER */
    $('#datepicker').datepicker({
        format: "mm-dd-yy"
    });
    /* INIT THE TIME */
    $('#timepicker1').timepicker();

    $scope.updateAssignment = function (newAsgmt) {
        debugger;
        httpFactory.updateAssignment(newAsgmt).success(function(response) {

            console.log('success');
        }).error(function(response) {
            console.log('error');
        })
        $("#createAsgmt").click();

    }
    $scope.uploadAssignment = function() {

    }
});

