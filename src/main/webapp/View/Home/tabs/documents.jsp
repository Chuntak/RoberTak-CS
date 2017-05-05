<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8"  %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Documents</title>

    <section>
        <link rel="stylesheet" href="<c:url value="/resources/app/css/documents.css" />">
    </section>

</head>
<body>
<div ng-controller="docCtrl">
    <c:choose>
        <%--IF PROFESSOR, APPLY THIS FUNCTIONALITY--%>
        <c:when test="${userType eq 'prof' && isOwner eq true}">
            <div id="add-content">
                <button type="button" class="btn btn-primary addDocBtn" id="addBtn">Add New Document</button>
                <div class="display-off" id="collapse-content">
                    <div class="col-lg-6">
                        <input type="text" class="form-control" ng-model="document.title" placeholder="Title" />
                        <textarea placeholder="Description" ng-model="document.description" class="description-text"></textarea>
                    </div>
                        <%--FILE UPLOAD--%>
                    <input type="file" file-model="document.file" class="pickFileBtn"/>
                    <button id="docSubmit" type="button" ng-click="uploadDocument()" class="btn btn-primary">Upload Document</button>
                    <input id="clearBtn" type="button" value="Cancel" ng-click="clearTextBox()"/>
                </div>
            </div>
        </c:when>
    </c:choose>
    <div id="doc-content">

        <%-- DISPLAY ALL DOCUMENTS --%>
        <div class="docCard" ng-repeat="document in documents" ng-include="getTemplate(document)"></div>
        <script type="text/ng-template" id="display">
            <div class="text-content">
                <h3>{{document.title}}</h3>Download Link:
                <a href="{{document.downloadLink}}">{{document.fileName}}</a>
                <p>{{document.description}}</p>
            </div>
            <c:choose>
                <%--<&-- IF PROFESSOR, APPLY THIS FUNCTIONALITY --&>--%>
                <c:when test="${userType eq 'prof' && isOwner eq true}">
                    <div class="toolBtn">
                        <%--<!-- DELETE DOCUMENT BUTTON-->--%>
                        <btn class="btn-md col-sm-1 glyphicon glyphicon-trash clickable on-show" ng-click="deleteDocument(document)"></btn>
                        <%--<!-- EDIT DOCUMENT BUTTON-->--%>
                        <btn class="btn-md col-sm-1 glyphicon glyphicon-pencil clickable on-show" ng-click="editDocument(document)"></btn>
                    </div>
                </c:when>
            </c:choose>
        </script>

        <c:choose>
            <%--<!-- IF PROFESSOR, APPLY THIS FUNCTIONALTY -->--%>
            <%--<!-- EDIT DOCUMENT -->--%>
            <c:when test="${userType eq 'prof' && isOwner eq true}">
                <script type="text/ng-template" id="edit">
                    <div class="col-lg-6">
                        <input type="text" class="form-control editInput" ng-model="selectedDocument.title"/>
                        <textarea type="text" ng-model="selectedDocument.description" class="description-edit"></textarea>
                    </div>
                    <div class="editBtn">
                        <input type="file" file-model="selectedDocument.file" class="replaceFileBtn"/>
                        <button ng-click="saveDocument($index,document)" class="btn btn-primary">Save</button>
                        <button ng-click="clearChanges($index,document)" class="btn btn-primary">Cancel</button>
                    </div>
                </script>
            </c:when>
        </c:choose>
    </div>

</div>
</body>

</html>