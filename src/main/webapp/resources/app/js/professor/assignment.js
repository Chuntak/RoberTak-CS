/**
 * Created by Chuntak on 4/21/2017.
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


angular.module('homeApp').controller('assignmentsCtrl', function ($scope, $http, global) {
    $scope.assignment = {};
    $scope.assignment.hwFilesList = [{}];

    $scope.uploadAssignments = function() {
        var file = $scope.assignment.hwFilesList[0].file;
        var fd = new FormData();
        fd.append('file', file);
        fd.append('f', 'json');
        $http.post("/uploadSyllabus", fd, {
            transformRequest : angular.identity,
            headers : {
                'Content-Type' : undefined
            },
            params : {
                "id" : $scope.assignment.id,
                "courseId" : global.getCourseId(),
                "title" : $scope.assignment.title,
                "description" : $scope.assignment.description,
                "gradableType" : $scope.assignment.gradableType,
                "maxGrade" : $scope.assignment.maxGrade,
                "dueDate" : $scope.assignment.dueDate,
                "dueTime" : $scope.assignment.dueTime,
                "difficulty" : $scope.assignment.difficulty,
                "hwBlobName" : $scope.assignment.hwFilesList[0].hwBlobName,
                "hwId" : $scope.assignment.hwFilesList[0].hwId
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

