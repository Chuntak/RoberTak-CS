/**
 * Created by Chuntak on 4/9/2017.
 */

angular.module('homeApp').directive('fileModel', ['$parse', function ($parse) {
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


angular.module('homeApp')
    .filter('trustUrl', function ($sce) {
        return function(url) {
            return $sce.trustAsResourceUrl(url);
        };
});

/* FACTORY TO HANDLE HTTP REQUEST LOGIC */
app.factory('httpSyllabusFactory', function($http, global) {
    /* SET SINGLETON LIKE OBJECT */
    var properties = this;

    /* getSyllabus - GET THE SYLLABUS FOR THE COURSE */
    properties.getSyllabus = function(){
        return $http.get("/getSyllabus", {
            params : {
                "courseId" : global.getCourseId()
            }
        });
    };

    /* deleteSyllabus - DELETES THE SYLLABUS FOR THE COURSE */
    properties.deleteSyllabus = function(){
        return $http.get("/deleteSyllabus", {
            params : {
                "courseId" : global.getCourseId()
            }
        });
    };

    /* uploadSyllabus - UPLOADS SYLLABUS FILE */
    properties.uploadSyllabus = function(syllabus) {
        /* CREATE FILE FORM DATA */
        var file = syllabus.myFile;
        var fd = new FormData();
        fd.append('file', file);
        fd.append('title', "title");
        fd.append("courseId", global.getCourseId());
        fd.append('f', 'json');
        /* MAKE HTTP REQUEST AND RETURN */
        return $http.post("/uploadSyllabus", fd, {
            transformRequest : angular.identity,
            headers : {
                'Content-Type' : undefined
            }
        });
    };
    return properties;
});

/* SYLLABUS CONTROLLER */
angular.module('homeApp').controller('syllabusCtrl', function ($scope, $http, global, httpSyllabusFactory) {

    $scope.syllabus = {};

    /* LOAD THE SYLLABUS FOR COURSE */
    httpSyllabusFactory.getSyllabus().success(function(response){
        $scope.syllabus = response;
    }).error(function(response){
        console.log(response);
    });

    /* deleteSyllabus - DELETE THE SYLLABUS */
    $scope.deleteSyllabus = function() {
        httpSyllabusFactory.deleteSyllabus().success(function(response){
            if(response === true) console.log("Success delete");
            $scope.syllabus = {};
            $scope.syllabus.viewLink = "none";
        }).error(function(response){
        });
    };

    /* uploadSyllabus - UPLOAD NEW SYLLABUS */
    $scope.uploadSyllabus = function() {
        httpSyllabusFactory.uploadSyllabus($scope.syllabus).success(function(response) {
            $scope.syllabus = response;
        }).error(function(response) {
            console.log(response);
        });
    }
});