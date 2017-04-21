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
    var submit = document.getElementById("courseSubmit");
    // Get the <span> element that closes the modal
    var span = document.getElementsByClassName("close")[0];
    // When the user clicks on the button, open the modal
    btn.onclick = function() {
        $scope.getTag();
        $scope.course = {};
        $scope.$apply();
        modal.style.display = "block";
    };
    // When the user clicks on the submit button, close the modal
    submit.onclick = function() {
        modal.style.display = "none";
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

    // /*INITALIZE THE SEMESTER YEAR FOR THE NEXT FOUR YEAR*/
    // /*Gets the select element*/
    // var x = document.getElementById("ano");
    // var date = new Date();
    // var year = date.getFullYear();
    // /*makes the year options*/
    // for(var i = year; i < year+4; i++) {
    //     var option = document.createElement("option");
    //     option.value = i.toString();
    //     option.text = i.toString();
    //     debugger;
    //     x.add(option);
    // }

/***************************************************************************************************************/
    $scope.course = {};
    $scope.lastEditedCourse = {};
    $scope.tagList = {};
    $scope.selectedTag = "";
    $scope.courseTaggedList = [];

    /*GETS THE TAGS FROM DATABASE FOR SELECTION*/
    $scope.getTag = function() {
        $http.get("/getTag").then(function (response){
            debugger;
            $scope.tagList = response.data;
        }, function(error) { console.log(error.data); });
    };
    $scope.getTag();
    /*ADD TAG TO DATABASE TO COURSE TODO NEEDS TO DO MORE*/
    $scope.addTag = function(){
        //todo http request
        if($scope.courseTaggedList.indexOf($scope.selectedTag) === -1){
            $scope.courseTaggedList.push($scope.selectedTag);
            $scope.selectedTag = "";
        }
    };


    /*ADDS OR EDITS */
    $scope.updateCourse = function(){
        var y = $http({
            method: 'GET',
            url: '/updateCourse',
            params: {"id": $scope.course.id, "prefix": $scope.course.prefix, "number":$scope.course.number, "name":$scope.course.name,
                "semester": $scope.course.semester, "ano":$scope.course.ano,"pub": $scope.course.public }
        }).then(function (response) {
            if(response.data !== "") {  /*add to the pane*/
                debugger;
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
                    "public": $scope.course.public
                });
            } else { /*edit the pane*/
                $scope.lastEditedCourse.prefix = $scope.course.prefix;
                $scope.lastEditedCourse.number = $scope.course.number;
                $scope.lastEditedCourse.name = $scope.course.name;
                $scope.lastEditedCourse.semester = $scope.course.semester;
                $scope.lastEditedCourse.ano = $scope.course.ano;
                $scope.lastEditedCourse.public = $scope.course.public;
            }
        }, function errorCallBack(response) {
            alert("add course error\n");
        });
    };

    /*SELECT COURSE TODO change course*/
    $scope.selected = 0;
    $scope.selectCourse = function(course, index){
        $scope.selected = index;
        global.setCourseId(course.id);
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
                "ano":course.ano ,"profFirstName":course.profFirstName, "profLastName":course.profLastName, "code":course.code, "public":course.public};
            $scope.courses.push(courseJson);
        }
        global.setCourseId($scope.courses[0].id);
        debugger;
    }, function(response) { /*error*/
    });

    /*EDITS A COURSE*/
    $scope.editCourse = function(course){
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
        $scope.course.public = course.public;
        $scope.course.ano = course.ano;
        /*DISPLAY THE MODAL*/
        var modal = document.getElementById('courseModal');
        modal.style.display = "block";

        var x = document.getElementById("ano");
        for(var i = 0; i < x.childElementCount; i++){
            if( x.getElementsByTagName("option")[i].value === course.ano){
                x.getElementsByTagName("option")[i].selected = true;
                break;
            }
        }
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
            }
        }, function errorCallBack(response) {
            alert("delete course error\n");
        });
    };


});


