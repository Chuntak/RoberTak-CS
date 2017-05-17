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
            <div class="document-create">
                    <%--ADD NEW DOCUMENT BUTTON--%>
                <div class="list-group-item-success">
                    <h4 class="panel-title" id="panel">
                        <a id="createDocument" data-toggle="collapse" data-target="#documentForm">Add Document</a>
                    </h4>
                </div>

                    <%-- ADD NEW DOCUMENT FORM --%>
                <div class="collapse" id="documentForm">
                    <form ng-submit="uploadDocument()">
                        <div class="form-group document-form">
                            <div class="col-xs-12">
                                <label>Title: </label>
                                <input type="text" class="form-control" ng-model="document.title" placeholder="Title" required />
                            </div>
                            <div class="col-xs-12">
                                <label class="label-padding">Description: </label>
                                <textarea placeholder="Description" ng-model="document.description" class="doc-edit"></textarea>
                            </div>
                                <%--FILE UPLOAD--%>
                            <input type="file" file-model="document.file" class="pickFileBtn"/>

                                <%-- SUBMIT FORM AND CANCEL BTNS--%>
                            <div class="document-btns">
                                <button id="docSubmit" type="button" class="btn btn-default">Upload Document</button>
                                <input type="button" value="Cancel" data-toggle="collapse" data-target="#DocumentForm" class="btn btn-default"/>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </c:when>
    </c:choose>
    <div id="doc-content">

    <%-- DISPLAY ALL DOCUMENTS --%>
    <div class="docCard" ng-repeat="document in documents">
        <div class="list-group-item document">
        <div id="documentViewer{{$index}}">
            <c:choose>
                <%--<&-- IF PROFESSOR, APPLY THIS FUNCTIONALITY --&>--%>
                <c:when test="${userType eq 'prof' && isOwner eq true}">
                            <%--<!-- DELETE DOCUMENT BUTTON-->--%>
                        <btn class="badge btn-md col-sm-1 glyphicon glyphicon-trash clickable on-show" ng-click="deleteDocument(document)"></btn>
                            <%--<!-- EDIT DOCUMENT BUTTON-->--%>
                        <btn class="badge btn-md col-sm-1 glyphicon glyphicon-pencil clickable on-show" data-toggle="collapse" data-target="#editDocument{{$index}}" ng-click="editDocument(document,$index)"></btn>
                </c:when>
            </c:choose>

            <h3 class="title{{$index}}" ng-bind="document.title"></h3>
            <h6>Download Link: <a href="{{document.downloadLink}}">{{document.fileName}}</a></h6>
            <h5 class="description-color" ng-bind="document.description"></h5>


            <div class="collapse" id="editDocument{{$index}}">
                <div class="form-group document-form">
                    <hr>

                    <div class="col-xs-12">
                        <label>Title: </label>
                        <input type="text" id="title{{$index}}" class="form-control editInput" ng-value="selectedDocument.title"/>
                    </div>

                    <div class="col-xs-12">
                        <label class="label-padding">Description: </label>
                        <textarea type="text" id="description{{$index}}" ng-value="selectedDocument.description" class="document-edit"></textarea>
                    </div>

                    <div class="col-lg-12">
                        <input type="file" file-model="selectedDocument.file"/>
                    </div>
                    <div class="document-btns">
                        <button type="submit" ng-click="saveDocument($index,document)" class="btn btn-default" data-toggle="collapse" data-target="#editDocument{{$index}}">Save</button>
                        <button class="btn btn-default" data-toggle="collapse" data-target="#editDocument{{$index}}" ng-click="cancelEdit($index)">Cancel</button>
                    </div>
                 </div>
            </div>
        </div>
        </div>
    </div>

</div>
</body>

</html>