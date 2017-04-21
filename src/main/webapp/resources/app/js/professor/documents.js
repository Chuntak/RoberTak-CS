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
    $scope.lastEditedDocument = {};
    $scope.selectedDocument = {};

    $scope.docFile = null;

    $scope.uploadDocument = function() {

        var file = $scope.doc.file;

        debugger;

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


    /*DELETES A DOCUMENT*/
    $scope.deleteDocument = function(document){
        debugger;
        var y = $http({
            method: 'GET',
            url: '/deleteDocument',
            params: {"id": document.id}
        }).then(function (response) {
            if(response.data === true) {  /*add*/
                for(var i = 0; i < $scope.documents.length; i++){
                    if($scope.documents[i].id === document.id){
                        $scope.documents.splice(i,1);
                        break;
                    }
                }
            }
        }, function errorCallBack(response) {
            alert("delete document error\n");
        });
    };

    /*GET DISPLAY OR EDIT*/
    $scope.getTemplate = function (document) {
        if (document.id === $scope.selectedDocument.id) return 'edit';
        else return 'display';
    };

    /*EDIT DOCUMENT*/
    $scope.editDocument = function (document) {
        $scope.selectedDocument = document;

    };

    /*SAVES FROM EDIT DOCUMENT*/
    $scope.saveDocument = function (index, document) {
        var x = $http({
            method: 'GET',
            url: '/updateDocument',
            params: {"title": $scope.selectedDocument.title, "description": $scope.selectedDocument.description, "courseId": $scope.global.getCourseId(), "id": $scope.selectedDocument.id}
        }).then(function (response) {
        //    empty
        }, function errorCallBack(response){
            alert("Edit Document Error\n");
        });

        $scope.reset();
    };

    /*RESET*/
    $scope.reset = function () {
        $scope.selectedDocument = {};
    };

    /* Add New Document Collapse */
    $(document).ready(function(){
        var count = 0;
        $("#collapse-content").hide();
        debugger;

        $("#addBtn").click(function(){
            debugger;
            count++;
            if(count % 2 != 0){
                debugger;
                $("#collapse-content").show();
                document.getElementById("doc-content").style.marginTop = "50px";
                document.getElementById("add-content").style.height = "150px";
                document.getElementById("doc-content").style.height = "430px";

            }else{
                debugger;
                $("#collapse-content").hide();
                document.getElementById("doc-content").style.marginTop = "0px";
                document.getElementById("add-content").style.height = "60px";
                document.getElementById("doc-content").style.height = "500px";

            }
        })
    })

});

