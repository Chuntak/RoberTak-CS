/**
 * Created by Calvin on 5/4/2017.
 */


var app = angular.module('homeApp');
/* FACTORY TO HANDLE HTTP REQUEST LOGIC */
app.factory('httpGradeFactory', function($http, global) {
    /* SET SINGLETON LIKE OBJECT */
    var properties = this;

    /* getGradable - loads the data for all the gradables */
    properties.getGradable = function(){
        return $http.get("/getGradable", {
            params : {
                "courseId" : global.getCourseId()
            }
        });
    };

    /* getGrades - loads the data for the grades*/
    properties.getGrades = function(courseId){
        return $http({
            method: 'GET',
            url: '/getGrade',
            params: {"courseId": global.getCourseId()}
        });
    };

    return properties;
});

/*Grades Controller*/
app.controller('gradesCtrl', function ($scope, $http, $state, global, httpGradeFactory) {
    $scope.gradeableList = {};

    /* ALL STUDENTS ENROLLED IN COURSE - FOR GRADING TABLES.
     GRADE INFO INSIDE WILL VARY DEPENDING ON CURRENT GRADABLE ITEM */
    $scope.gradable = {};

    /* INIT THE DATEPICKER */
    $('#datepicker').datepicker({
        format: "mm-dd-yy"
    });

    /* INIT THE TIME */
    $('#timepicker1').timepicker();


    /* TIME FORMATTING OPTIONS */
    var options = {
        weekday: "long", year: "numeric", month: "short",
        day: "numeric", hour: "2-digit", minute: "2-digit"
    };

    /* getGradable - GETS THE GRADABLES AND THE GRADES OF ALL ENROLLED */
    httpGradeFactory.getGradable().success(function(response) {
        $.each(response, function() {
            this.dueDate = new Date(this.dueDate).toLocaleTimeString("en-us", options);
            this.time = new Date(this.dueDate).toLocaleTimeString();
            this.date = new Date(this.dueDate).toLocaleDateString();
        });
        /* MODEL THE GRADABLES */
        $scope.gradables = response

        /* GETS THE GRADES FOR ALL GRADABLES*/
        httpGradeFactory.getGrades(global.getCourseId()).success(function (response) {
            /* ADD THE GRADES TO THE GRADABLE OBJECT*/
            for(i = 0; i < response.length; i++){
                $scope.gradables[i].grade = response[i].grade;
            }
        }).error(function(response){
            console.log(response);
        });
    }).error(function(response){
        console.log(response);
    });

});