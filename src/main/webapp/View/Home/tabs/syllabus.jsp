<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8"  %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Syllabus</title>

    <section>
        <link rel="stylesheet" href="<c:url value="/resources/app/css/syllabus.css" />">
    </section>
</head>
<body>
<h2>Syllabus</h2>

<div ng-controller="syllabusCtrl">
    <c:choose>
        <c:when test="${userType eq 'prof'}">
            <form ng-submit="uploadSyllabus()">
                <h5>Upload New Syllabus:</h5>
                <input class="file-selection" type="file" file-model="syllabus.myFile"/>
                <button type="submit" class="btn btn-primary submitBtn">Submit</button>
            </form>
        </c:when>
    </c:choose>

    <iframe ng-src="{{syllabus.viewLink | trustUrl}}" frameborder="1"></iframe>
</div>


</body>


</html>