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
    $scope.uploadSyllabus = function() {
        debugger;
        var file = $scope.myFile;
        // var fd = new FormData();
        // fd.append('file', file);
        // var y = $http({
        //     method: 'POST',
        //     url: '/uploadSyllabus',
        //     params: {"file" : file}
        // }).then(function (response) {
        //     debugger;
        // }, function errorCallBack(response) {
        //     //alert("error checking user account http request\n");
        // });

        var fd = new FormData();
        fd.append('file', file);
        // fd.append('filetype', 'csv');
        fd.append('f', 'json');

        $http.post("/uploadSyllabus", fd, {
            transformRequest : angular.identity,
            headers : {
                'Content-Type' : undefined
            },
            params : {
                "title" : "hi"
            }
        }).success(function() {
            debugger;
            console.log('success');
        }).error(function() {
            console.log('error');
        });
    }
});