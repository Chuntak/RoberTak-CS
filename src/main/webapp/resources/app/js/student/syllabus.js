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


angular.module('homeApp').controller('syllabusCtrl', function ($scope, $http) {
    $scope.myFile = null;
    $scope.message = "syllabus.js file ctrl";

});