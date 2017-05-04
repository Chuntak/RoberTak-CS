/**
 * Created by Calvin on 4/1/2017.
 */
/*Course Controller*/


angular.module('homeApp').controller('courseCtrl', function ($scope, $http, $state, global) {

/******************************************INITALIZING THE MODAL************************************************/
    // Get the modal
    var modal = document.getElementById('courseModal');
    // Get the button that opens the modal
    var btn = document.getElementById("addCourse");
    // Get the <span> element that closes the modal
    var span = document.getElementsByClassName("close")[0];
    // When the user clicks on the button, open the modal
    btn.onclick = function() {
        $scope.getTag();
        $scope.course = {};
        $scope.courseTaggedList = [];
        $scope.$apply();
        modal.style.display = "block";
    };
    // When the user clicks on <span> (x), close the modal
    span.onclick = function() {
        modal.style.display = "none";
    };
    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function(event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    };


/***************************************************************************************************************/
    $scope.course = {id:0};
    $scope.lastEditedCourse = {};
    $scope.tagList = {};
    $scope.selectedTag = "";
    $scope.courseTaggedList = [];

    /*GETS THE TAGS FROM DATABASE FOR SELECTION*/
    $scope.getTag = function() {
        $http.get("/getTag").then(function (response){
            $scope.tagList = response.data;
        }, function(error) { console.log(error.data); });
    };
    $scope.getTag();
    /*ADD TAG */
    $scope.addTag = function(tag){
        if($scope.courseTaggedList.indexOf($scope.selectedTag) === -1 && $scope.selectedTag !== ""){
            $scope.courseTaggedList.push($scope.selectedTag);
        }
        $scope.selectedTag = "";
    };

    $scope.removeTag = function(courseTagged) {
        debugger;
        for(var i = 0; i < $scope.courseTaggedList.length; i++){
            if($scope.courseTaggedList[i] === courseTagged){
                $scope.courseTaggedList.splice(i,1);
                break;
            }
        }
    };


    /*ADDS OR EDITS */
    $scope.updateCourse = function(){
        var crsid = $scope.course.id;
        var y = $http({
            method: 'GET',
            url: '/updateCourse',
            params: {"id": $scope.course.id, "prefix": $scope.course.prefix, "number":$scope.course.number, "name":$scope.course.name,
                "semester": $scope.course.semester, "ano":$scope.course.ano,"pub": $scope.course.pub, "tagNames" : $scope.courseTaggedList  }
        }).then(function (response) {
            modal.style.display = "none";
            if(response.data.id !== crsid) {  /*add to the pane*/
                var code = response.data.code;
                var id = response.data.id;
                var firstName = response.data.profFirstName;
                var lastName = response.data.profLastName;
                $scope.courses.push({
                    "id": id,
                    "prefix": $scope.course.prefix,
                    "number": $scope.course.number,
                    "name": $scope.course.name,
                    "semester": $scope.course.semester,
                    "ano" : $scope.course.ano,
                    "profFirstName": firstName,
                    "profLastName": lastName,
                    "code": code,
                    "pub": $scope.course.pub
                });
                /* DETERMINE IF ADDING NEW COURSE TO SET SELECTED COURSE PROPERLY*/
                if(!$scope.course.id){
                    /* SET SELECTED COURSE TO NEWLY ADDED COURSE */
                    $scope.selectCourse($scope.courses[$scope.courses.length-1], $scope.courses.length-1);
                }
                /*clear the courseTaggedList since modal is closed*/
                $scope.courseTaggedList = [];
            } else { /*edit the pane*/
                $scope.lastEditedCourse.prefix = $scope.course.prefix;
                $scope.lastEditedCourse.number = $scope.course.number;
                $scope.lastEditedCourse.name = $scope.course.name;
                $scope.lastEditedCourse.semester = $scope.course.semester;
                $scope.lastEditedCourse.ano = $scope.course.ano;
                $scope.lastEditedCourse.pub = $scope.course.pub;
                /*clear the courseTaggedList since the modal is closed*/
                $scope.courseTaggedList = [];
            }
        }, function errorCallBack(response) {
            alert("add course error\n");
        });
    };

    /*SELECT COURSE TODO change course*/
    $scope.selected = 0;
    $scope.selectCourse = function(course, index){
        if(course) /*IF COURSE IS NOT NULL THEN ITS TRUE*/ {
            $scope.selected = index;
            global.setCourseId(course.id);
        } else {
            $scope.selected = -1;
            global.setCourseId(-1);
        }
        /* RELOAD TAB DATA */
        var reloadData = function(){
            $state.reload();
        };
        reloadData();
    };

    $http.get('/getCourse').then(function(response) {
        var courseList = response.data;
        $scope.courses = [];
        for(i = 0; i < courseList.length; i++) {
            var course = courseList[i];
            var courseJson = {"id": course.id ,"prefix":course.prefix, "number":course.number, "name":course.name, "semester":course.semester,
                "ano":course.ano ,"profFirstName":course.profFirstName, "profLastName":course.profLastName, "code":course.code, "pub":course.pub};
            $scope.courses.push(courseJson);
        }
        global.setCourseId($scope.courses[0].id);
    }, function(response) { /*error*/
    });

    /*EDITS A COURSE*/
    $scope.editCourse = function(course){
        global.setCourseId(course.id);
        $state.reload();
        $scope.lastEditedCourse = course; /*saves when returned we change*/
        $scope.course = {};
        $scope.course.id = course.id;
        $scope.course.prefix = course.prefix;
        $scope.course.number = course.number;
        $scope.course.name = course.name;
        $scope.course.semester = course.semester;
        $scope.course.profFirstName = course.profFirstName;
        $scope.course.profLastName = course.profLastName;
        $scope.course.code = course.code;
        $scope.course.pub = course.pub;
        $scope.course.ano = course.ano;

        $http.get("/getTag", { params: {"taggableId" : global.getCourseId() , "taggableType" : "course"} }).then(function (response){
            debugger;
            $scope.courseTaggedList = response.data;
            /*DISPLAY THE MODAL*/
            $scope.getTag();
            var modal = document.getElementById('courseModal');
            modal.style.display = "block";
        }, function(error) { console.log(error.data); });
    };

    /*DELETES A COURSE*/
    $scope.deleteCourse = function(course){
        var y = $http({
            method: 'GET',
            url: '/deleteCourse',
            params: {"id": course.id, "code" : course.code}
        }).then(function (response) {
            if(response.data === true) {  /*add*/
                for(var i = 0; i < $scope.courses.length; i++){
                    if($scope.courses[i].id === course.id){
                        $scope.courses.splice(i,1);
                        break;
                    }
                }
                /*SETS THE SELECTED COURSE TO THE FIRST ONE*/
                /*@TODO fix the blinking selectCourse gets called before this*/
                $scope.selectCourse($scope.courses[0], 0);

            }
        }, function errorCallBack(response) {
            alert("delete course error\n");
        });
    };


});


