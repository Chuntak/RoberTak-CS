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
    <link rel="stylesheet" href="<c:url value="/resources/app/css/assignments.css" />">
    <link rel="stylesheet" href="<c:url value="/resources/app/css/imported/bootstrap-datepicker.css" />">
    <link rel="stylesheet/less" href="<c:url value="/resources/app/css/imported/timepicker.less" />">

    <!--KENDO UI -->
    <link rel="stylesheet" href="https://kendo.cdn.telerik.com/2017.1.118/styles/kendo.common-material.min.css" />
    <link rel="stylesheet" href="https://kendo.cdn.telerik.com/2017.1.118/styles/kendo.material.min.css" />
    <link rel="stylesheet" href="https://kendo.cdn.telerik.com/2017.1.118/styles/kendo.material.mobile.min.css" />

    <%--<link href="https://cdn.datatables.net/1.10.14/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css">--%>
    <%--<link href="https://cdn.datatables.net/buttons/1.3.1/css/buttons.dataTables.min.css" rel="stylesheet" type="text/css">--%>
    <%--<link href="https://cdn.datatables.net/select/1.2.2/css/select.dataTables.min.css" rel="stylesheet" type="text/css">--%>
    <%--<link rel="stylesheet/less" href="<c:url value="/resources/app/css/imported/editor.dataTables.min.css" />">--%>

    <title>Grades</title>
</head>
<body>
<div ng-controller="gradesCtrl">
    <c:choose>
        <%--IF PROFESSOR, APPLY THIS FUNCTIONALITY--%>
        <c:when test="${userType eq 'prof'}">
            <div class="asgmt-create">
                <div class="list-group-item-success">
                    <h4 class="panel-title">
                        <a id="createAsgmt" data-toggle="collapse" data-target="#assignmentForm">Add New Grade Task</a>
                    </h4>
                </div>
                <div class="collapse" id="assignmentForm">
                        <%-- FORM FOR NEW GRADABLE --%>
                    <div>
                        <div class="form-group asgmt-form">
                                <%-- GRADABLE TITLE --%>
                            <input type="text" class="form-control" ng-model="gradable.title" placeholder="Title" required />
                            <input type="text" class="form-control" ng-model="gradable.maxGrade" placeholder="Maximumm Grade" required />
                            <textarea type="text" ng-model="gradable.description"></textarea>
                        </div>

                            <%-- DATE PICKER --%>
                        <div class="col-xs-3 input-group date asgmt-form" id="datepicker" data-provide="datepicker">
                            <label for="date" class="col-2 col-form-label">Due Date</label>
                            <input ng-model="gradable.date" id="date" required type="text" class="form-control">
                            <div class="input-group-addon">
                                <span class="glyphicon glyphicon-th"></span>
                            </div>
                        </div>
                            <%-- TIME PICKER --%>
                        <div class="col-xs-3 input-group bootstrap-timepicker timepicker asgmt-form">
                            <label for="timepicker1" class="col-2 col-form-label">Due Time</label>
                            <input ng-model="gradable.time" required id="timepicker1" type="text" class="form-control input-small">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
                        </div>

                            <%-- TYPE SELECTOR --%>
                        <form class="form-inline">
                            <label class="mr-sm-2" for="inlineFormCustomSelect">Choose Type:  </label>
                            <select class="custom-select mb-2 mr-sm-2 mb-sm-0" id="inlineFormCustomSelect" ng-model="gradable.gradableType">
                                <option selected value="att">Attendance</option>
                                <option value="exam">Exam</option>
                                <option value="ec" >Extra Credit</option>
                                <option value="other">Other</option>
                            </select>
                        </form>

                            <%-- SUBMIT FORM & CANCEL BTNS --%>
                        <div class="asgmt-btns">
                            <button type="submit" class="btn btn-default" ng-click="updateGradable(gradable)">Submit</button>
                            <button class="btn btn-default" data-toggle="collapse" data-target="#assignmentForm">Cancel</button>
                        </div>
                    </div>
                </div>
            </div>
        </c:when>
    </c:choose>

    <%-- Load the Grade Titles and descriptions --%>
    <div class="gradeContainer" data-toggle="collapse" href="#items" ng-repeat="gradable in gradables" ng-include="setIndex()" ></div>
    <script type="text/ng-template" id="display" >
        <div class="list-group-item assignment">
            <%--<span ng-click="deleteAssignment(gradable)" class="badge btn-xs col-sm-1 glyphicon glyphicon-trash clickable on-show"></span>--%>
            <%--<span class="badge btn-xs col-sm-1 glyphicon glyphicon-pencil clickable on-show"></span>--%>
            <h4 ng-bind="gradable.title"></h4>
            <h5 ng-bind="'Maximum Grade: ' + gradable.maxGrade"></h5>
            <h6 ng-bind="'Due: ' + gradable.dueDate"></h6>
        </div>
    </script>
    <div id="items" class="panel-collapse collapse">
        <h3>Items</h3>

        <kendo-grid id="grid" options="mainGridOptions">

        </kendo-grid>

    </div>
</div>

</body>

<%-- NEEDED FOR GRADING TABLE --%>
<%--<script src="https://cdn.datatables.net/1.10.14/js/jquery.dataTables.min.js"></script>--%>
<%--<script src="https://cdn.datatables.net/buttons/1.3.1/js/dataTables.buttons.min.js"></script>--%>
<%--<script src="https://cdn.datatables.net/select/1.2.2/js/dataTables.select.min.js"></script>--%>
<%--<script src="<c:url value="/resources/app/js/imported/dataTables.editor.min.js" />"  type="text/javascript" ></script>--%>

</html>