<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8"  %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Grades</title>
</head>
<body>
    <div ng-controller="gradeCtrl">
        <c:choose>
            <%--IF PROFESSOR, APPLY THIS FUNCTIONALITY--%>
            <c:when test="${userType eq 'prof'}">
                <%--Adding an announcement--%>
                <div id="addGradeDiv">
                    <form ng-submit="addGrade()" id="addGradeForm">
                        <div class="row">
                            <input required id="addGradeTitle"type="text" class="col-xs-12" placeholder="Grade Title" maxlength="32">
                        </div>
                        <button class="btn-md btn-primary" type="submit">Add Grade</button>
                    </form>
                </div>
                <button class="btn-info btn-block" ng-click="toggleAdd()">Show/Hide Announcement Form</button>
            </c:when>
        </c:choose>

        <%--Load the Grade--%>
        <div>

        </div>
    </div>
</body>


</html>