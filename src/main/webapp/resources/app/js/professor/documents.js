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
    $scope.oldDocument = {};
    $scope.selectedDocument = {};


    $scope.uploadDocument = function() {
        var file = $scope.document.file;
        var fd = new FormData();
        fd.append('file', file);
        fd.append('f', 'json');
        if($scope.document.file!==undefined) {
            $http.post("/uploadDocument", fd, {
                transformRequest: angular.identity,
                headers: {
                    'Content-Type': undefined
                },
                params: {
                    "title": $scope.document.title,
                    "description": $scope.document.description,
                    "courseId": global.getCourseId()
                }
            }).success(function (response) {
                debugger;
                var documentJson = response;
                $scope.documents.push(documentJson);
                document.getElementById("collapse-content").className = "display-off";
                console.log('success')
            }).error(function (response) {
                console.log('error');
            });
        }
        else {
            $scope.document.fileName = "Not Available"
        }
    };

    /* FROM CONTROLLER */
    $http.get('/getDocument',{
        params : {
            "courseId":global.getCourseId()
        }
    }).then(function(response) {
        var documentList = response.data;
        $scope.documents = [];
        for(i = 0; i < documentList.length; i++) {
            var document = documentList[i];
            var documentJson = {"title": document.title ,"description": document.description,
                "downloadLink": document.downloadLink, "viewLink" : document.viewLink, "fileName": document.fileName, "id": document.id};
            $scope.documents.push(documentJson);
        }
        // $scope.global.document = $scope.documents[0];

    }, function(response) { /*error*/
    });


    /*DELETES A DOCUMENT*/
    $scope.deleteDocument = function(document){
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
        $scope.oldDocument = {};
        $scope.oldDocument.description = document.description;
        $scope.oldDocument.downloadLink = document.downloadLink;
        $scope.oldDocument.fileName = document.fileName;
        $scope.oldDocument.id = document.id;
        $scope.oldDocument.title = document.title;
        $scope.oldDocument.viewLink = document.viewLink;

        $scope.selectedDocument = document; //jQuery.extend(true, {}, document);
        debugger;
    };

    /*SAVES FROM EDIT DOCUMENT*/
    $scope.saveDocument = function (index, document) {
        var file = $scope.selectedDocument.file;
        if(file) {
            var fd = new FormData();
            fd.append('file', file);
            fd.append('f', 'json');
            var x = $http.post("/uploadDocument", fd,
                {
                    transformRequest: angular.identity,
                    headers: {
                        'Content-Type': undefined
                    }, params: {
                    "title": $scope.selectedDocument.title,
                    "blobName": $scope.selectedDocument.blobName,
                    "description": $scope.selectedDocument.description,
                    "courseId": $scope.global.getCourseId(),
                    "id": $scope.selectedDocument.id
                }
                }
            ).then(function (response) {
                document.downloadLink = response.data.downloadLink;
                document.fileName = response.data.fileName;
                document.blobName = response.data.blobName;
                debugger;
                //    empty
            }, function errorCallBack(response) {
                alert("Edit Document Error\n");
            });
        } else {
            var y = $http.get("/updateDocument",
                {
                     params: {
                        "title": $scope.selectedDocument.title,
                        "blobName": $scope.selectedDocument.blobName,
                        "description": $scope.selectedDocument.description,
                        "courseId": $scope.global.getCourseId(),
                        "id": $scope.selectedDocument.id
                    }
                }
            ).then(function (response) {
                debugger;
            }, function errorCallBack(response) {
                alert("Edit Document Error\n");
            });
        }
        $scope.reset();
    };

    /*RESET*/
    $scope.reset = function () {
        $scope.selectedDocument = {};
    };

    /*THIS IS THE CLEAR BTN*/
    $scope.clearChanges = function(index, document) {
        $scope.reset();
        document.description = $scope.oldDocument.description;
        document.downloadLink = $scope.oldDocument.downloadLink;
        document.fileName = $scope.oldDocument.fileName;
        document.id = $scope.oldDocument.id;
        document.title = $scope.oldDocument.title;
        document.viewLink = $scope.oldDocument.viewLink;
        $scope.oldDocument = {};
    };

    /* Add New Document Collapse */
    var docAddBtn = document.getElementById("addBtn");
    docAddBtn.onclick = function() {
        var collapseContent = document.getElementById("collapse-content");
        if (collapseContent.className === 'display-off') {
            collapseContent.className = 'display-on';
            document.getElementById("doc-content").style.marginTop = "50px";
            document.getElementById("add-content").style.height = "150px";
            document.getElementById("doc-content").style.height = "430px";
        } else {
            collapseContent.className = 'display-off';
            document.getElementById("doc-content").style.marginTop = "0px";
            document.getElementById("add-content").style.height = "60px";
            document.getElementById("doc-content").style.height = "500px";
        }
    };
});

