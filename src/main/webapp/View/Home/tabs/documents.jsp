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
<c:choose>
    <c:when test="${userType eq 'prof'}">
        <div ng-controller="docCtrl">
            <div class="col-lg-6">
                <input type="text" class="form-control" ng-model="doc.title" placeholder="Title" />
                <textarea rows="2" cols="53" placeholder="Descriptions" ng-model="doc.description"></textarea>
            </div>

            <form ng-submit="uploadDoc()">
                <input type="file" file-model="docFile"/>
            </form>

            <button id="docSubmit" type="button" ng-click="uploadDocument()">Upload Document</button>
            <input id="clearBtn" type="button" value="Cancel" ng-click="clearTextBox()"></input>
        </div>
    </c:when>
</c:choose>

</body>



</html>