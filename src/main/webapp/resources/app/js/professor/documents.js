/**
 * Created by Lin on 4/14/2017.
 */

/*SET NEW MODEL*/
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

/*Document Controller
* ADD DOCUMENT */
angular.module('homeApp').controller('docCtrl', function ($scope, $http, global) {
    $scope.global = global;
    var clearBtn = document.getElementById("clearBtn");
    var submit = document.getElementById("docSubmit");

    $scope.docFile = null;

    $scope.uploadDocument = function() {

        var file = $scope.docFile;

        var fd = new FormData();
        fd.append('file', file);
        fd.append('f', 'json');
        $http.post("/uploadDocument", fd, {
            transformRequest : angular.identity,
            headers : {
                'Content-Type' : undefined
            },
            params : {
                "title" : $scope.doc.title,
                "description": $scope.doc.description,
                "courseId" : global.getCourseId()
            }
        }).success(function(response){
            console.log('success')
        }).error(function(response) {
            console.log('error');
        });
        debugger;
    };

    // /*EDIT DOCUMENT*/
    // $scope.updateDocument = function(){
    //     var x = $http({
    //         method: 'GET',
    //         url: '/updateDocument',
    //         params: {"title": $scope.doc.title, "description": $scope.doc.description }
    //     }).then(function (response) {
    //         if(response.data != "") {
    //
    //         }
    //     })
    // }

    clearBtn.onclick = function() {
        clearBtn.value = "";
    };

    submit.onclick = function() {

    };

});