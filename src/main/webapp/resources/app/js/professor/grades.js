/**
 * Created by Calvin on 5/4/2017.
 */


var app = angular.module('homeApp');
/*Grades Controller*/
app.controller('gradesCtrl', function ($scope, $http, $state, global) {
    $scope.gradeableList = [];
    $scope.grades = {};
    $scope.gradable = {};
    $scope.isEmpty = -1;
    $scope.edit_index = -1;

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

    debugger;
    /*Get all enrolled in the course and their respective grade*/

    /**
     * GETS THE GRADEABLES AND THE GRADES OF ALL ENROLLED
     * 1. Get the gradeables
     * 2. Get all people enrolled
     * 3. For each person enrolled, display their grade
     */
    $http.get("/getGradable", {
        params : {
            "courseId" : global.getCourseId()
        }
    }).then(function(response) {
        debugger;
        $.each(response.data, function() {
            this.dueDate = new Date(this.dueDate).toLocaleTimeString("en-us", options);
        });
        $scope.gradables = response.data;
    }, function(response) { /*error*/
    });

    $scope.displayNewForm = function (index) {
        var newGrade = {};
        $scope.gradables.unshift(newGrade);
        $scope.edit_index = index;
    };

    $scope.setIndex=function(){
        edit_index=20;
        return 'display';
    };

    $scope.updateGradable = function(gradable){

        var y = $http.get("/updateGradable",{
                transformRequest: angular.identity,
                headers: {
                    'Content-Type': undefined
                },
                params: {"title": gradable.title,
                    "gradableType": gradable.gradableType,
                    "maxGrade" : gradable.maxGrade,
                    "dueDate" : new Date(gradable.date+" "+gradable.time).getTime(),
                    "description" : gradable.description,
                    "courseId" : global.getCourseId()
                }
            }).success(function (response) {

            var gradableJson = {
                "id": response.id,
                "title": response.title,
                "type": response.gradableType,
                "maxGrade" : response.maxGrade,
                "description" : response.description,
                "dueDate" : new Date(response.dueDate).toLocaleTimeString("en-us", options)
            };

            $scope.gradables.unshift(gradableJson);
        });
    }



    /******* GRADES ********/
    $http.get('/getGrade',{
        params : {
            "courseId":global.getCourseId(),
        }
    }).then(function(response) {
        var gradeList = response.data;
        $scope.grades = [];
        for(i = 0; i < gradeList.length; i++) {
            var grade = gradeList[i];
            var gradeJson = {"firstName": grade.firstName ,"lastName": grade.lastName,
                "email": grade.email, "submissionFile": grade.submissionFile};
            $scope.grades.push(gradeJson);
        }
    }).then(function(response) { /*error*/
    });


});