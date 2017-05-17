/**
 * Created by Calvin on 4/1/2017.
 */

/* FACTORY TO HANDLE HTTP REQUEST LOGIC */
angular.module('homeApp').factory('httpCourseFactory', function($http, global) {
    /* SET SINGLETON LIKE OBJECT */
    var properties = this;

    /* getCourse - GET LIST OF COURSES PROFESSOR TEACHES */
    properties.getCourse = function(){
        return $http.get('/getCourse');
    };

    properties.enrollCourse = function(crsCode){
        return $http({
            method: 'GET',
            url: '/enrollCourse',
            params: {"code": crsCode}
        });
    };
    return properties;
});


/*Course Controller*/
angular.module('homeApp').controller('courseCtrl', function ($scope, $http, $templateCache, $state, global, httpCourseFactory) {
    $scope.course = "";
    /* enrollCourse - ENROLLS A STUDENT IN A COURSE */
    $scope.enrollCourse = function() {
        httpCourseFactory.enrollCourse($scope.course.code).success(function (response) {
            var course = response;
            if(course !== "") {
                $scope.courses.push({
                    "id": course.id,
                    "prefix": course.prefix,
                    "number": course.number,
                    "name": course.name,
                    "semester": course.semester,
                    "ano":course.ano,
                    "profFirstName": course.profFirstName,
                    "profLastName": course.profLastName
                });
                /* DETERMINE IF ADDING NEW COURSE TO SET SELECTED COURSE PROPERLY*/
                if(!global.setCourseId(course.id) || course.id != global.setCourseId(course.id)){
                    /* SET SELECTED COURSE TO NEWLY ADDED COURSE */
                    $scope.selectCourse($scope.courses[$scope.courses.length-1], $scope.courses.length-1);
                }
            } else {
                console.log("Course already added or incorrect course code");
            }
        }).error(function(response){
            console.log(response);
        });
    };

    $scope.selected = 0;
    $scope.selectCourse = function(course, index){
        $scope.selected = index;
        global.setCourseId(course.id);
        if($state.current.name === "quizTaker") {
            $templateCache.remove("/quizTak");
        }
        /* RELOAD TAB DATA */
        var reloadData = function(){
            $state.reload();
        };
        reloadData();
    };

    /* GET THE COURSES STUDENT IS ENROLLED IN */
    httpCourseFactory.getCourse().success(function(response) {
        /* SAVE NAME OF USER */
        var firstName = sessionStorage.getItem("userFirstName");
        var lastName = sessionStorage.getItem("userLastName");
        var courseList = response;
        $scope.courses = [];
        for(i = 0; i < courseList.length; i++) {
            var course = courseList[i];
            var courseJson = {
                "id": course.id,
                "prefix":course.prefix,
                "number":course.number,
                "name":course.name,
                "semester":course.semester,
                "ano":course.ano,
                "profFirstName":course.profFirstName,
                "profLastName":course.profLastName
            };
            /* ADD COURSE TO COURSE MODEL */
            $scope.courses.push(courseJson);
        }
        /* SET CURRENT COURSE ID */
        global.setCourseId($scope.courses[0].id);

    }).error(function(response){
        console.log(response);
    });

});

