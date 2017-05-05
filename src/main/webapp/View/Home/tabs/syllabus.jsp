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
        <c:choose>
            <c:when test="${userType eq 'prof' && isOwner eq true}">
                <link rel="stylesheet" href="<c:url value="/resources/app/css/professor.css" />">
            </c:when>
            <c:when test="${userType eq 'stud' || isOwner eq false}">
                <link rel="stylesheet" href="<c:url value="/resources/app/css/student.css" />">
            </c:when>
        </c:choose>
    </section>
</head>
<body>

<div ng-controller="syllabusCtrl">
    <c:choose>
        <c:when test="${userType eq 'prof' && isOwner eq true}">
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