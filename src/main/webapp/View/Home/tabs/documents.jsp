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
        <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet" type="text/css">
        <link rel="stylesheet" href="<c:url value="/resources/app/css/home.css" />">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
        <link rel="stylesheet" href="<c:url value="/resources/app/css/documents.css" />">
        <c:choose>
            <c:when test="$(userType eq 'prof')">
                <link rel="stylesheet" href="<c:url value="/resources/app/css/professor.css" />">
            </c:when>
            <c:when test="$(userType eq 'stud')">
                <link rel="stylesheet" href="<c:url value="/resources/app/css/student.css" />">
            </c:when>
        </c:choose>
    </section>

</head>
<body>
<div ng-controller="docCtrl">
    <c:choose>
        <c:when test="${userType eq 'prof'}">
            <div id="add-content">
                <button type="button" class="btn btn-primary addDocBtn" id="addBtn">Add New Document</button>
                <div id="collapse-content">
                    <div class="col-lg-6">
                        <input type="text" class="form-control" ng-model="doc.title" placeholder="Title" />
                        <textarea placeholder="Description" ng-model="doc.description" class="description-text"></textarea>
                    </div>

                    <%--FILE UPLOAD--%>
                    <input type="file" file-model="doc.file" class="pickFileBtn"/>

                    <button id="docSubmit" type="button" ng-click="uploadDocument()" class="btn btn-primary">Upload Document</button>
                    <input id="clearBtn" type="button" value="Cancel" ng-click="clearTextBox()"/>
                </div>
            </div>
        </c:when>
    </c:choose>
    <div id="doc-content">
        <div class="docCard" ng-repeat="document in documents" ng-include="getTemplate(document)"></div>
            <script type="text/ng-template" id="display">
                    <div class="text-content">
                        <h3>{{document.title}}</h3>Download Link:
                        <a href="{{document.downloadLink}}">{{document.fileName}}</a>
                        <p>{{document.description}}</p>
                    </div>
                    <c:choose>
                        <c:when test="${userType eq 'prof'}">
                            <div class="toolBtn">
                                <btn class="btn-md col-sm-1 glyphicon glyphicon-trash clickable on-show" ng-click="deleteDocument(document)"></btn>
                                <btn class="btn-md col-sm-1 glyphicon glyphicon-pencil clickable on-show" ng-click="editDocument(document)"></btn>
                            </div>
                        </c:when>
                    </c:choose>
            </script>

            <c:choose>
                <c:when test="${userType eq 'prof'}">
                    <script type="text/ng-template" id="edit">
                        <div class="col-lg-6">
                            <input type="text" class="form-control editInput" ng-model="document.title"/>
                            <textarea type="text" ng-model="document.description" class="description-edit"></textarea>
                        </div>
                        <div class="editBtn">
                            <input type="file" file-model="doc.file" class="replaceFileBtn"/>
                            <button ng-click="saveDocument($index,document)" class="btn btn-primary">Save</button>
                            <button ng-click="reset()" class="btn btn-primary">Cancel</button>
                        </div>
                    </script>
                </c:when>
            </c:choose>
    </div>

</div>
</body>

<section>
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script>
    <script src="https://apis.google.com/js/api:client.js"></script>
    <%--Google Logout End--%>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <!-- App Base -->
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-router/0.4.2/angular-ui-router.min.js"></script>
    <c:choose>
        <c:when test="${userType eq 'prof'}">
            <script src="<c:url value="/resources/app/js/professor/home.js" />"  type="text/javascript" ></script>
            <script src="<c:url value="/resources/app/js/professor/course.js" />"  type="text/javascript" ></script>
            <script src="<c:url value="/resources/app/js/professor/syllabus.js" />"  type="text/javascript" ></script>
            <script src="<c:url value="/resources/app/js/professor/documents.js" />"  type="text/javascript" ></script>
        </c:when>
        <c:when test="${userType eq 'stud'}">
            <script src="<c:url value="/resources/app/js/student/home.js" />"  type="text/javascript" ></script>
            <script src="<c:url value="/resources/app/js/student/course.js" />"  type="text/javascript" ></script>
        </c:when>
    </c:choose>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


</html>