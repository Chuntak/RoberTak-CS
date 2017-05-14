/**
 * Created by Calvin on 5/4/2017.
 */
var app = angular.module('homeApp');

/* FACTORY TO HANDLE HTTP REQUEST LOGIC */
app.factory('httpGradeFactory', function($http, global) {
    /* SET SINGLETON LIKE OBJECT */
    var properties = this;

    properties.getGradable = function(){
        return $http.get("/getGradable", {
            params : {
                "courseId" : global.getCourseId()
            }
        });
    }

    properties.getEnrolled = function(){
        return $http.get("/getEnrolled", {
            params : {
                "id" : global.getCourseId()
            }
        });
    }

    /* updateAssignment - adds or updates an assignment */
    properties.updateGradable = function(gradable) {
        return $http.get("/updateGradable",{
            transformRequest: angular.identity,
            headers: {
                'Content-Type': undefined
            },
            params: {
                "id" : gradable.id,
                "title": gradable.title,
                "gradableType": gradable.gradableType,
                "maxGrade" : gradable.maxGrade,
                "dueDate" : new Date(gradable.date + " " + gradable.time).getTime(),
                "description" : gradable.description,
                "courseId" : global.getCourseId()
            }
        });
    };

    return properties;
});

/*Grades Controller*/
app.controller('gradesCtrl', function ($scope, $http, $state, global, httpGradeFactory) {
    /* ALL GRADABLE ITEMS */
    $scope.gradables = [];
    /* ALL STUDENTS ENROLLED IN COURSE - FOR GRADING TABLES.
       GRADE INFO INSIDE WILL VARY DEPENDING ON CURRENT GRADABLE ITEM */
    $scope.students = [];
    $scope.gradable = {};
    $scope.selectedGradable = {};


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

    /* DATA FOR KENDO GRID. THESE SHOULD BE THE ITEM OBJECTS RECEIVED FROM DATASTORE */
    $scope.initGrid = function() {
        $scope.gridData = new kendo.data.DataSource({
            data: $scope.students,
            schema: {
                model: {
                    id: "id",
                    fields: {
                        id: {
                            type: "number",
                            editable : false
                        },
                        firstName: {
                            type: "string",
                            editable: false
                        },
                        lastName: {
                            //data type of the field {Number|String|Boolean} default is String
                            type: "string",
                            // used when new model is created
                            editable: false
                        },
                        email:{
                            type: "string",
                            editable : false
                        },
                        submission:{
                            type:"string",
                            editable : false
                        },
                        grade:{
                            type: "number",
                            editable: true
                        }
                    }
                }
            },
            batch: true
        });
        /* INIT ITEM TABLE WITH KENDO GRID */
        $scope.mainGridOptions = {
            dataSource: $scope.gridData,
            selectable: "row",
            columns: [
                { field: "id", title: "ID", hidden: true },
                { field: "firstName", title: "First Name" },
                { field: "lastName", title: "Last Name" },
                { field: "email", title: "Email" },
                { field: "submission", title: "Submission"},
                { field: "grade", title: "Grade" }
            ],
            save: function(e){
                $scope.updateTempRecords();
                var dataSource = $("#grid").data("kendoGrid").dataSource;
                $("#grid").data('kendoGrid').refresh();
                debugger;
            },
            remove: function (e) {
                if(e.model.iD != 0) {
                    var deletedItem = {
                        "id":e.model.iD
                    };
                    $scope.deletedItems.push(deletedItem);
                }
            },
            update: function(e){
                //alert("update");
            },
            cancel: function(e) {
                console.log("cancel fired");
                if($scope.tempSavedRecords != null){
                    $('#grid').data('kendoGrid').dataSource.data($scope.tempSavedRecords);
                }
                else{
                    $('#grid').data('kendoGrid').dataSource.cancelChanges();
                }
            },
            sortable: true,
            pageable: true,
            editable: true,
            resizable: true
        };
    };



    /*Get all enrolled in the course and their respective grade*/

    /**
     * GETS THE GRADABLES AND THE GRADES OF ALL ENROLLED
     * 1. Get the gradables
     * 2. Get all people enrolled
     * 3. For each person enrolled, display their grade
     */
    httpGradeFactory.getGradable().success(function(response) {
        $.each(response, function() {
            this.dueDate = new Date(this.dueDate).toLocaleTimeString("en-us", options);
            this.time = new Date(this.dueDate).toLocaleTimeString();
            this.date = new Date(this.dueDate).toLocaleDateString();
        });
        /* MODEL THE GRADABLES */
        $scope.gradables = response;
        /* NOW LOAD ALL ENROLLED STUDENT INFORMATION AFTER GRADABLES LOAD */
        httpGradeFactory.getEnrolled().success(function(response){
            $scope.students = response;
            $scope.initGrid();
        }).error(function(){

        });
    }).error(function(response){
        console.log(response);
    });

    /* CREATE NEW GRADE TASK */
    $scope.updateGradable = function(gradable){
        httpGradeFactory.updateGradable(gradable).success(function (response) {

            /* SUCCESSFUL HTTP REQUEST - ADD TO MODEL */
            var gradableJson = {
                "id": response.id,
                "title": response.title,
                "gradableType": response.gradableType,
                "maxGrade" : response.maxGrade,
                "dueDate" : new Date(response.dueDate).toLocaleTimeString("en-us", options),
                "description" : response.description,
                "courseId" : global.getCourseId(),
                "date" : gradable.date,
                "time" : gradable.time
            };
            /* ADD TO THE LIST */
            $scope.gradables.unshift(gradableJson);

            /* CLEAR FORM DISPLAY*/
            $scope.gradable = {};
        }).error(function(response){
            console.log(response);
            debugger;
        });
    }

    /* EDIT GRADE DESCRIPTION/INFORMATION */
    $scope.editGradable = function(gradable, index){

        gradable.title = document.getElementById("title" + index).value;
        gradable.maxGrade = document.getElementById("max" + index).value;
        gradable.description = document.getElementById("description" + index).value;

        var y = $http.get("/updateGradable",{
            transformRequest: angular.identity,
            headers: {
                'Content-Type': undefined
            },
            params: {
                "id" : gradable.id,
                "title": gradable.title,
                "gradableType": gradable.gradableType,
                "maxGrade" : gradable.maxGrade,
                "dueDate" : new Date(gradable.date+" "+gradable.time).getTime(),
                "description" : gradable.description,
                "courseId" : global.getCourseId()
            }
        }).success(function (response) {
            $state.reload();
            $scope.gradables[index].title = response.title;
            $scope.gradables[index].gradableType = response.gradableType;
            $scope.gradables[index].maxGrade = response.maxGrade;
            $scope.gradables[index].dueDate = new Date(response.date+" "+response.time).getTime();
            $scope.gradables[index].description = response.description;
            $scope.gradables[index].date = response.date;
            $scope.gradables[index].time = response.time;

        }).error(function(response){
            console.log(response);
        });

        document.getElementById("title" + index).value = gradable.title;
        document.getElementById("max" + index).value = gradable.maxGrade;
        document.getElementById("description" + index).value = gradable.description;

    };

    /*DELETES A DOCUMENT*/
    $scope.deleteGradable = function(gradable){
        var y = $http({
            method: 'GET',
            url: '/deleteGradable',
            params: {"id": gradable.id}
        }).then(function (response) {
            if(response.data === true) {  /*add*/
                for(var i = 0; i < $scope.gradables.length; i++){
                    if($scope.gradables[i].id === gradable.id){
                        $scope.gradables.splice(i,1);
                        break;
                    }
                }
            }
        }, function errorCallBack(response) {
            console.log(response);
        });
    };

    /******* GRADES ********/
    // $http.get('/getGrade',{
    //     params : {
    //         "courseId":global.getCourseId(),
    //     }
    // }).success(function(response) {
    //     var gradeList = response.data;
    //     $scope.grades = [];
    //     for(i = 0; i < gradeList.length; i++) {
    //         var grade = gradeList[i];
    //         var gradeJson = {"firstName": grade.firstName ,"lastName": grade.lastName,
    //             "email": grade.email, "submissionFile": grade.submissionFile};
    //         $scope.grades.push(gradeJson);
    //     }
    // }).error(function(response) { /*error*/
    //     console.log(response);
    //     debugger;
    // });


});