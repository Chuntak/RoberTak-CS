/**
 * Created by Calvin on 5/4/2017.
 */


var app = angular.module('homeApp');
/*Grades Controller*/
app.controller('gradesCtrl', function ($scope, $http, $state, global) {
    $scope.gradeableList = {};
    /*Get all enrolled in the course and their respective grade*/

    /**
     * GETS THE GRADEABLES AND THE GRADES OF ALL ENROLLED
     * 1. Get the gradeables
     * 2. Get all people enrolled
     * 3. For each person enrolled, display their grade
     */
    $http.get("/getGradeables", {
        params : {
            "courseId" : global.getCourseId()
        }
    }).success(function(response){
        $scope.gradeableList = response;
    }).error(function(response){
    });
});