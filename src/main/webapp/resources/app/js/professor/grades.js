/**
 * Created by Calvin on 5/4/2017.
 */
var app = angular.module('homeApp');
var selectedId = 0;
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
    };

    properties.getEnrolled = function(){
        return $http.get("/getEnrolled", {
            params : {
                "id" : global.getCourseId()
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
    $scope.gridData = null;


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

    /*ACTIVATES WHEN THE GRADE TITLE IS CLICKED AND THE GRADE TABLE SHOWS*/
    $scope.getGrades = function(gradableId, gridIndex){
        selectedGradableID = gradableId;
        httpGradeFactory.getGrades(gradableId).success(function (response) {
            $scope.students = response;
            $scope.initDataSource();
            $scope.gridData.read();
            $scope.initGrid();

        }).error(function(response){
            console.log(response);
        });
    };

    $scope.initDataSource = function(){
        $scope.gridData = new kendo.data.DataSource({
            data: $scope.students,
            pageSize: 5 ,
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
                        submissionFile:{
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
    }
    /* DATA FOR KENDO GRID. THESE SHOULD BE THE ITEM OBJECTS RECEIVED FROM DATASTORE */
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
                { field: "submissionFile", title: "Submission"},
                { field: "grade", title: "Grade" }
            ],
            save: function(e){
                debugger;
                var id = selectedGradableID;
                $scope.updateGradableRecords(e.model.id, id  , e.values.grade);
                // var dataSource = $("#grid").data("kendoGrid").dataSource;
                // $("#grid").data('kendoGrid').refresh();
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
        // httpGradeFactory.getEnrolled().success(function(response){
        //     debugger;
        //     $scope.students = response;
        //      $scope.initGrid();
            // var data2 = [
            //     {
            //         label: "Grade",
            //         strokeColor: '#F16220',
            //         data: [
            //             { x:100, y: 1 },
            //             { x: 85, y: 3 },
            //             { x: 80, y: 8 },
            //             { x: 75, y: 10 },
            //             { x: 70, y: 7 },
            //             { x: 65, y: 6 },
            //             { x: 60, y: 5 },
            //             { x: 50, y: 3 },
            //             { x: 40, y: 1 }
            //         ]
            //     }
            // ];
            // var graphCanvas = '#gradeGraph-0';
            // var ctx = $(graphCanvas).get(0).getContext("2d");
            // var myLineChart = new Chart(ctx).Scatter(data2, {
            //     bezierCurve: true,
            //     showTooltips: true,
            //     scaleShowHorizontalLines: true,
            //     scaleShowLabels: true,
            //     scaleBeginAtZero: true
            // });
            // makeGraph();
        // }).error(function(){
        //
        // });
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
            $("#createGradable").click();
            $scope.gradable = {};
        }).error(function(response){
            console.log(response);
            debugger;
        });
    };

    $scope.initEdit = function(index){
        /* INIT DATEPICKER */
        $('#datepicker' + index).datepicker({format: "mm-dd-yy"});
        /* INIT THE TIME */
        $('#timepicker1' + index).timepicker();
    };

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

    // Chart.defaults.global.animation = false;
    // Chart.defaults.global.tooltipFontSize=10;
    // Chart.defaults.global.tooltipFillColor = "rgba(0,80,140,0.8)";
    // Chart.defaults.global.responsive = true;
    // Chart.defaults.global.scaleLineColor = "black";
    //
    // /*CREATES A GRAPH FOR A GRADABLE*/
    // function makeGraph(){
    //     var gradeFrequency=[{ grade: 100, freq: 1 },{ grade: 90, freq: 3 },
    //         { grade: 80, freq: 8 },{ grade: 70, freq: 11 },
    //         { grade: 60, freq: 9 },{ grade: 50, freq: 5 },
    //         { grade: 40, freq: 3 },{ grade: 30, freq: 3 },
    //         { grade: 20, freq: 2 },{ grade: 10, freq: 1 }];
    //
    //     var graphPoints = [];
    //     var xData;
    //     var yData;
    //     debugger;
    //     for(count = 0; count < gradeFrequency.length -1; count++){
    //         xData = gradeFrequency[count].grade;
    //         yData = gradeFrequency[count].freq;
    //         graphPoints.push({x : xData, y : yData});
    //     }
    //     debugger;
    //
    //
    //     var graphData = [
    //         {
    //             label: "gradable.title",
    //             strokeColor: '#F16220',
    //             data: graphPoints
    //         }
    //     ];
    //     // var graphCanvas = '#gradeGraph-'+index;
    //     var graphCanvas = '#gradeGraph-0';
    //     var ctx = $(graphCanvas).get(0).getContext("2d");
    //     ctx.height = 400;
    //     var myLineChart = new Chart(ctx).Scatter(graphData, {
    //         bezierCurve: true,
    //         showTooltips: true,
    //         scaleShowHorizontalLines: true,
    //         scaleShowVerticalLines: false,
    //         scaleShowLabels: true,
    //         scaleBeginAtZero: true,
    //         scaleStartValue : 0,
    //         maintainAspectRatio: true,
    //
    //         options : {
    //             scales:{
    //                 xAxes:[{
    //                     ticks:{
    //                         beginAtZero: true
    //                     }
    //                 }],
    //                 yAxes: [{
    //                     ticks: {
    //                         beginAtZero: true
    //                     }
    //                 }]
    //             }
    //         }
    //     });
    //
    //
    // }

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

    $scope.updateGradableRecords = function(studentId,gradableId, grade){
        var y = $http({
            method: 'GET',
            url: '/updateGrade',
            params: {"id" : studentId, "gradableId" : gradableId, "grade" : grade}
        }).then(function (response) {
            debugger;
        }, function errorCallBack(response) {
            console.log(response);
        });
    };

});