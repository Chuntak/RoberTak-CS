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


angular.module('homeApp').controller('syllabusCtrl', function ($scope, $http, global) {
    $http.get("/getSyllabus", {
        params : {
            "courseId" : global.getCourseId()
        }
    }).success(function(response){
        $scope.syllabus = response;
    }).error(function(response){
    });
});