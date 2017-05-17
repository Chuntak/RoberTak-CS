/**
 * Created by Calvin on 5/4/2017.
 */
var app = angular.module('homeApp');
var selectedGradable = {};
var selectedGradableId = 0;
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

    /* getGrades - loads the data for the selected gradable */
    properties.getGrades = function(gradableId){
        return $http({
            method: 'GET',
            url: '/getGrade',
            params: {"courseId": global.getCourseId() ,"gradableId": gradableId}
        });
    };


    properties.getStatistics = function(gradableId){
        return $http({
            method: 'GET',
            url: '/getStatistic',
            params: {"courseId": global.getCourseId() ,"id": gradableId}
        });
    };

    return properties;
});

/*Grades Controller*/
app.controller('gradesCtrl', function ($scope, $http, $state, global, httpGradeFactory) {
    /* ALL GRADABLE ITEMS */
    $scope.gradables = [];
    /* ARRAY OF GRADE MODEL OBJECTS*/
    $scope.students = [];
    /* THE CURRENT GRADABLE OBJECT*/
    $scope.gradable = {};
    /* THE GRID DATA FOR KENDO GRID*/
    $scope.gridData = null;
    /* THE INDEX OF THE KENDO GRID WE WANT TO EDIT*/
    var gridIndex = 0;


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

    /* getGrades - ACTIVATES WHEN THE GRADE TITLE IS CLICKED AND THE GRADE TABLE SHOWS*/
    $scope.getGrades = function(gradable,index){
        selectedGradableID = gradable.id;
        selectedGradable = gradable;
        gridIndex = index;
        httpGradeFactory.getGrades(selectedGradableID).success(function (response) {
            /* Loop through the $scope.students, pop off old grades and push in the new grades*/

            if($scope.students.length){
                var slength = $scope.students.length;
                var rlength = response.length;
                var max = slength > rlength ? slength : rlength;
                for(i = 0; i < max; i++){
                    if(slength--) {
                        $scope.students.shift();
                    }
                    if(rlength--){
                        $scope.students.push(response[i]);
                    }
                }
                $("#kendogrid"+gridIndex).data("kendoGrid").dataSource.read();
            }
            /* RUNS DURING INITIAL LOAD AND BINDS THE DATA TO KENDO*/
            else{
                $scope.students = response;
                $scope.initDataSource();
                $scope.initGrid();

            }


        }).error(function(response){
            console.log(response);
        });
    };

    /* initDataSouce - BINDS THE $SCOPE.STUDENTS DATA TO THE KENDO GRID*/
    $scope.initDataSource = function(){
        $scope.gridData = new kendo.data.DataSource({
            data: $scope.students,
            pageSize: 30 ,
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
                        fileName:{
                            type:"string",
                            editable : false
                        },
                        downloadLink:{
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
    };
    /* initGrid - DATA FOR KENDO GRID. THESE SHOULD BE THE ITEM OBJECTS RECEIVED FROM DATABASE */
    $scope.initGrid = function() {

        /* INIT ITEM TABLE WITH KENDO GRID */
        $scope.mainGridOptions = {
            dataSource: $scope.gridData,
            selectable: "row",
            columns: [
                { field: "id", title: "ID", hidden: true },
                { field: "firstName", title: "First Name" },
                { field: "lastName", title: "Last Name" },
                { field: "email", title: "Email" },
                { field: "fileName", title: "Submission",template:"<a href='${downloadLink}' target='_blank'>${fileName}</a>"},
                { field: "grade", title: "Grade" }
            ],
            save: function(e){
                var id = selectedGradableID;
                $scope.updateGrade(e.model.id, id  , e.values.grade);
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

    /* getGradable- GETS ALL THE GRADABLES FOR THIS COURSE RUNS ON INITIAL LOAD */
    httpGradeFactory.getGradable().success(function(response) {
        $.each(response, function() {
            this.dueDate = new Date(this.dueDate).toLocaleTimeString("en-us", options);
            this.time = new Date(this.dueDate).toLocaleTimeString();
            this.date = new Date(this.dueDate).toLocaleDateString();
        });
        /* MODEL THE GRADABLES */
        $scope.gradables = response;
    }).error(function(response){
        console.log(response);
    });

    /* updateGradable- CREATE NEW GRADE TASK */
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
            $scope.gradables.push(gradableJson);

            /* CLEAR FORM DISPLAY*/
            $("#createGradable").click();
            $scope.gradable = {};
        }).error(function(response){
            console.log(response);
            debugger;
        });
    };

    /* initEdit - INITIALIZES THE EDIT FOR THE NEW GRADABLE*/
    $scope.initEdit = function(index,gradable){
        /* INIT DATEPICKER */
        $('#datepicker' + index).datepicker({format: "mm-dd-yy"});
        /* INIT THE TIME */
        $('#timepicker1' + index).timepicker();

        document.getElementById("title" + index).value = gradable.title;
        document.getElementById("maxGrade" + index).value = gradable.maxGrade;
        document.getElementById("description" + index).value = gradable.description;
        document.getElementById("date" + index).value = gradable.date;
        document.getElementById("timepicker1" + index).value = gradable.time;
        document.getElementById("type" + index).value = gradable.gradableType;
    };

    /* editGradable - EDIT GRADE DESCRIPTION/INFORMATION */
    $scope.editGradable = function(gradable, index){

        gradable.title = document.getElementById("title" + index).value;
        gradable.maxGrade = document.getElementById("maxGrade" + index).value;
        gradable.description = document.getElementById("description" + index).value;
        gradable.date = document.getElementById("date" + index).value;
        gradable.time = document.getElementById("timepicker1" + index).value;
        gradable.gradableType = document.getElementById("type" + index).value;

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


    };

    /* deleteGradable - DELETES A GRADABLE */
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

    /* updateGrade - UPDATES THE GRADES WITH THE NEW GRADE*/
    $scope.updateGrade = function(studentId,gradableId, grade){
        var y = $http({
            method: 'GET',
            url: '/updateGrade',
            params: {"id" : studentId, "gradableId" : gradableId, "grade" : grade}
        }).success(function (response) {
            /* REFRESH */
            httpGradeFactory.getStatistics(gradableId).success(function (response) {
                /*Replace the statistic data*/
                selectedGradable.avg = response[0].avg;
                selectedGradable.highestGrade = response[0].highestGrade;
                selectedGradable.minGrade = response[0].minGrade;
                selectedGradable.stdDev = response[0].stdDev;
            }).error(function (response) {
                console.log(response);
            });
        }).error(function(response){
            console.log(response);
        });
    };

    $scope.cancelEdit = function(index){
        /*PUT BACK THE DISPLAY ON*/
        document.getElementById("gradableViewer" + index).style.display = 'initial';
    };

    $scope.cancelAdd = function(){
        $scope.gradable = {};
        $('#createGradable').attr("disabled", false);
    }
});