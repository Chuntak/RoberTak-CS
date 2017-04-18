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


angular.module('homeApp').controller('syllabusCtrl', function ($scope, $http, global) {
    $scope.global = global;
    $scope.message = "syllabus.js file ctrl";

    $scope.syllabus = {};

    $http.get("/getSyllabus", {
        params : {
                "courseId" : $scope.global.course.id
        }
    }).success(function(response){
        $scope.syllabus = response;
        debugger;
    }).error(function(response){
        debugger;
    });

    $scope.deleteSyllabus = function() {
        $http.get("/deleteSyllabus", {
            params : {
                "courseId" : $scope.global.course.id
            }
        }).success(function(response){
            if(response === true) console.log("Success delete");
            $scope.syllabus = {};
            $scope.syllabus.viewLink = "none";
            debugger;
        }).error(function(response){
            debugger;
        });
    };

    $scope.uploadSyllabus = function() {
        var file = $scope.syllabus.myFile;
        var fd = new FormData();
        fd.append('file', file);
        fd.append('f', 'json');
        $http.post("/uploadSyllabus", fd, {
            transformRequest : angular.identity,
            headers : {
                'Content-Type' : undefined
            },
            params : {
                "title" : "hi",
                "courseId" : $scope.global.course.id
            }
        }).success(function(response) {
            debugger;
            $scope.syllabus = response;
            console.log('success');
        }).error(function(response) {
            console.log('error');
        });
    }
});