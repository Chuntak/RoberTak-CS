<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8"  %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">

    <!-- Include stylesheet -->
    <link rel="stylesheet" href="<c:url value="/resources/app/css/grades.css" />">

    <title>Grades</title>
</head>
<body>
    <div ng-controller="gradesCtrl">
        <c:choose>
            <%--IF PROFESSOR, APPLY THIS FUNCTIONALITY--%>
            <c:when test="${userType eq 'prof'}">
                <%--Adding an announcement--%>
                <div id="addGradeDiv">
                    <form name="addGrade" ng-submit="addGrade()" id="addGradeForm">
                        <div>
                            <input required ng-model="grade.title" id="addGradeTitle"type="text" class="col-xs-12" placeholder="Grade Title" maxlength="32">
                        </div>
                        <div>
                            Grade Type
                        </div>
                        <div>
                            <%--hw", "quiz", "ec", "exam", "att", "other--%>
                            <label class="radio-inline">
                                <input type="radio" name="type" ng-model="grade.type" value="hw">Homework
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="type" ng-model="grade.type" value="quiz">Quiz
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="type" ng-model="grade.type" value="ec">Extra Credit
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="type" ng-model="grade.type" value="exam">Exam
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="type" ng-model="grade.type" value="att">Attendence
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="type" ng-model="grade.type" value="other" checked>Other
                            </label>
                        </div>
                        <button class="btn-md btn-primary" type="submit">Add Grade</button>
                    </form>
                </div>
                <button class="btn-info btn-block" ng-click="toggleAdd()">Show/Hide Grade Adding</button>
            </c:when>
        </c:choose>

        <%--Load the Grade--%>
        <div class="gradeContainer" ng-repeat="gradable in gradableList">

        </div>
    </div>
</body>


</html>