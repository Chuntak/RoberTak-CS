/**
 * Created by Chuntak on 4/21/2017.
 */
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
    $scope.document = {};
    $scope.documents = [];
    $scope.editorEnabled = false;
    $scope.selectedDocument = {};

    $scope.docFile = null;


    /* FROM CONTROLLER */
    $http.get('/getDocument',{
        params : {
            "courseId":global.getCourseId()
        }
    }).then(function(response) {
        debugger;
        var documentList = response.data;
        $scope.documents = [];
        for(i = 0; i < documentList.length; i++) {
            var document = documentList[i];
            var documentJson = {"title": document.title ,"description": document.description,
                "downloadLink": document.downloadLink, "viewLink" : document.viewLink, "fileName": document.fileName, "id": document.id};

            $scope.documents.push(documentJson);
        }
        // $scope.global.document = $scope.documents[0];
        debugger;
    }, function(response) { /*error*/
    });


    /*GET DISPLAY OR EDIT*/
    $scope.getTemplate = function (document) {
        if (document.id === $scope.selectedDocument.id) return 'edit';
        else return 'display';
    };


});

