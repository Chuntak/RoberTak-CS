<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8"  %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Syllabus</title>
</head>
<body>
<p>This is syllabus</p>

<p>Select a file to upload to your Google Cloud Storage bucket.</p>
<div ng-controller="syllabusCtrl">
    <c:choose>
        <c:when test="${userType eq 'prof'}">
            <form ng-submit="uploadSyllabus()">
                <input type="file" file-model="syllabus.myFile"/>
                <button type="submit">Submit</button>
            </form>
            <button ng-click="deleteSyllabus()">Delete</button>
        </c:when>
    </c:choose>

    <iframe ng-src="{{syllabus.viewLink | trustUrl}}" frameborder="1"></iframe>
</div>


</body>


</html>