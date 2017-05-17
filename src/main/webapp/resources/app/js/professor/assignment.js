/**
 * Created by Chuntak on 4/21/2017.
 */
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
    /* getAssignments - returns array of assignments */
    properties.getAssignments = function(){
        //return $http.get('/getAssignments', {"crsId" : global.getCourseId(), "gradableType" : "hw"});
        return $http.get("/getAssignments", {
            params : {
                "crsId" : global.getCourseId(),
                "gradableType" : "hw"
            }
        });
    };
    /* updateAssignment - adds or updates an assignment */
    properties.updateAssignment = function(newAsgmt) {
        /* CONVERT TIME TO PROPER FORMAT HH:MM:SS */
        var tmpDate = new Date("1/1/1111 " + newAsgmt.time);
        //var time = tmpDate.getHours() + ":" + tmpDate.getMinutes() + ":" + "00";

        /* CREATE PARAMETERS FOR HTTP REQUEST */
        var params = {
            "id" : newAsgmt.id,
            "title" : newAsgmt.title,
            "description" : newAsgmt.description,
            "courseId" : global.getCourseId(),
            "gradableType" : "hw",
            "maxGrade" : newAsgmt.maxGrade,
            "dueDate" : new Date(newAsgmt.date + " " + newAsgmt.time).getTime(),
            "difficulty" : "hard",
            "submittable" : newAsgmt.submittable
        };
        /* MAKE THE FILE STUFF */
        if(newAsgmt.file){
            var fd = new FormData();
            fd.append('hwFile', newAsgmt.file);
            fd.append('f', 'json');
            /* RETURN THE HTTP REQUEST TO ANGULAR CONTROLLER */
            return $http.post("/uploadAssignment", fd, {
                transformRequest : angular.identity,
                headers : {
                    'Content-Type' : undefined
                },
                params : params
            });
        }
        else {
            return $http.post('/updateAssignment', params);
        }
    };
    /* deleteAssignment - deletes an assigment and any attached files previously uploaded */
    properties.deleteAssignment = function(assignment){
        return $http({
            method: 'GET',
            url: '/deleteAssignment',
            params: {
                "id" : assignment.id,
                "hwBlobName" : assignment.hwBlobName
            }
        })
    };
    return properties;
});

/* CONTROLLER FOR ASSIGNMENTS TAB */
app.controller('assignmentsCtrl', function ($scope, $http, global, httpAssignmentFactory) {

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

    /* LIST OF ASSIGNMENTS */
    $scope.assignments = [];
    /* SELECTED ASSIGNMENT */
    $scope.assignment = {};
    /* KEEP TRACK OF NEW ASSIGNMENT */
    $scope.newAsgmt = {};

    /* GET ASSIGNMENTS FOR COURSE */
    httpAssignmentFactory.getAssignments().success(function(response){
        /* FORMAT DUE DATE */
        $.each(response, function() {
            this.dueDate = new Date(this.dueDate).toLocaleTimeString("en-us", options);
            this.time = new Date(this.dueDate).toLocaleTimeString();
            this.date = new Date(this.dueDate).toLocaleDateString();
        });
        $scope.assignments = response;
    }).error(function(response){
    });

    /* CREATE/EDIT ASSIGNMENTS */
    $scope.updateAssignment = function (newAsgmt) {
        var checkVal = document.getElementById("checkSub").checked;
        if(checkVal === true){
            newAsgmt.submittable = true;
        }
        else{
            newAsgmt.submittable = false;
        }
        httpAssignmentFactory.updateAssignment(newAsgmt).success(function(response) {
            /* SUCCESSFUL HTTP REQUEST - ADD TO MODEL */
            $scope.assignments.unshift({
                "id" : response.id,
                "title" : response.title,
                "description" : response.description,
                "courseId" : response.courseId,
                "gradableType" : "hw",
                "maxGrade" : response.maxGrade,
                "dueDate" : new Date(response.dueDate).toLocaleTimeString("en-us", options),
                "difficulty" : "hard",
                "hwBlobName" : response.hwBlobName,
                "hwDownloadLink" : response.hwDownloadLink,
                "hwFileName" : "",
                "submittable" : response.submittable,
                "date" : newAsgmt.date,
                "time" : newAsgmt.time
            });
            /* CLOSE FORM */
            $("#createAsgmt").click();
        }).error(function(response) {
            console.log('error');
        })
    };

    /* SAVE THE STATE FOR CANCEL EDIT */
    $scope.initEdit = function(index, asgmt){
        /* INIT DATEPICKER */
        $('#datepicker' + index).datepicker({format: "mm-dd-yy"});
        /* INIT THE TIME */
        $('#timepicker1' + index).timepicker();

        document.getElementById("title" + index).value = asgmt.title;
        document.getElementById("maxGrade" + index).value = asgmt.maxGrade;
        document.getElementById("description" + index).value = asgmt.description;
        document.getElementById("date" + index).value = asgmt.date;
        document.getElementById("timepicker1" + index).value = asgmt.time;
    };

    /* CREATE/EDIT ASSIGNMENTS */
    $scope.editAssignment = function (newAsgmt, index) {

        newAsgmt.title = document.getElementById("title" + index).value;
        newAsgmt.maxGrade = document.getElementById("maxGrade" + index).value;
        newAsgmt.description = document.getElementById("description" + index).value;
        newAsgmt.date = document.getElementById("date" + index).value;
        newAsgmt.time = document.getElementById("timepicker1" + index).value;

        var checkVal = document.getElementById("checkSub" + index).checked;
        if(checkVal === true){
            newAsgmt.submittable = true;
        }
        else{
            newAsgmt.submittable = false;
        }

        httpAssignmentFactory.updateAssignment(newAsgmt).success(function(response) {
            /* SUCCESSFUL HTTP REQUEST - EDIT MODEL */

            /* UPDATE INFORMATION */
            newAsgmt.assignments = response.assignments;
            newAsgmt.dueDate = new Date(newAsgmt.date + " " + newAsgmt.time).toLocaleTimeString("en-us", options);


        }).error(function(response) {
            console.log('error');
        });
    };

    /* REMOVE ASSIGNMENT */
    $scope.deleteAssignment = function(assignment){
        httpAssignmentFactory.deleteAssignment(assignment).then(function(response){
            for(var i = 0; i < $scope.assignments.length; i++){
                if($scope.assignments[i].id === response.data){
                    $scope.assignments.splice(i,1);
                    break;
                }
            }
        }).then(function(response){
            console.log("deleteAssignment Error: " + response);
        });
    };

    /* CANCEL EDIT / RESTORE INFORMATION */
    $scope.cancelEdit = function(index){
        /*PUT BACK THE DISPLAY ON*/
        document.getElementById("asgmtViewer" + index).style.display = 'initial';
    };
});

