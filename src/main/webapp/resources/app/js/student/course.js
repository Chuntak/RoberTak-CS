/**
 * Created by Calvin on 4/1/2017.
 */
/*Course Controller*/
angular.module('homeApp').controller('courseCtrl', function ($scope, $http, $state, global) {
    $scope.course = "";
    /*enrolling in a course*/
    $scope.enrollCourse = function() {
        var y = $http({
            method: 'GET',
            url: '/enrollCourse',
            params: {"code": $scope.course.code}
        }).then(function (response) {
            var course = response.data;
            if(course !== "") {
                $scope.courses.push({
                    "id": course.id,"prefix": course.prefix, "number": course.number, "name": course.name, "semester": course.semester,
                    "ano":course.ano,"profFirstName": course.profFirstName, "profLastName": course.profLastName
                });
                debugger;
            } else {
                console.log("Course already added or incorrect course code");
            }

        }, function errorCallBack(response) {
            alert("Course load error\n");
        });
    };

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

    /*gets the course to display*/
    $http.get('/getCourse').then(function(response) {
        var firstName = sessionStorage.getItem("userFirstName");
        var lastName = sessionStorage.getItem("userLastName");
        var courseList = response.data;
        $scope.courses = [];
        for(i = 0; i < courseList.length; i++) {
            var course = courseList[i];
            var courseJson = {"id": course.id, "prefix":course.prefix, "number":course.number, "name":course.name, "semester":course.semester,
                "ano":course.ano ,"profFirstName":course.profFirstName, "profLastName":course.profLastName};
            $scope.courses.push(courseJson);
        }
    }, function(response) { /*error*/
    });

});

