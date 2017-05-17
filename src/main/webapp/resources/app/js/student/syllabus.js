/**
 * Created by Chuntak on 4/9/2017.
 */
/*make it a trusted url*/
angular.module('homeApp')
    .filter('trustUrl', function ($sce) {
        return function(url) {
            return $sce.trustAsResourceUrl(url);
        };
    });

/* SYLLABUS CONTROLLER */
angular.module('homeApp').controller('syllabusCtrl', function ($scope, $http, global) {
    /* LOAD THE SYLLABUS */
    $http.get("/getSyllabus", {
        params : {
            "courseId" : global.getCourseId()
        }
    }).success(function(response){
        /* SET THE SYLLABUS */
        $scope.syllabus = response;
    }).error(function(response){
    });
});