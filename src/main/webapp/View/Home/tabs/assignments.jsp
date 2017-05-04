<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8"  %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Assignments</title>
    <link rel="stylesheet" href="<c:url value="/resources/app/css/assignments.css" />">
    <link rel="stylesheet" href="<c:url value="/resources/app/css/imported/bootstrap-datepicker.css" />">
    <link rel="stylesheet/less" href="<c:url value="/resources/app/css/imported/timepicker.less" />">
</head>
<body ng-controller="assignmentsCtrl">
    <div class="list-group">

        <div class="asgmt-create">
            <div class="list-group-item-success">
                <h4 class="panel-title">
                    <a id="createAsgmt" data-toggle="collapse" data-target="#assignmentForm">Add New Assignment</a>
                </h4>
            </div>

            <div class="collapse" id="assignmentForm">
                <%-- FORM FOR NEW ASSIGNMENT --%>
                <form ng-submit="updateAssignment(newAsgmt)">
                    <div class="form-group asgmt-form">
                        <%-- ASSIGNMENT TITLE --%>
                        <input type="text" class="form-control" ng-model="newAsgmt.title" placeholder="Title" required />
                        <%-- ASSIGNEMTN DESCRIPTION --%>
                        <textarea required class="asgmt-form form-control" rows="5" ng-model="newAsgmt.descr" placeholder="Write description here"></textarea>
                        <%-- DATE PICKER --%>
                        <div class="input-group date asgmt-form" id="datepicker" data-provide="datepicker">
                            <label for="date" class="col-2 col-form-label">Due Date</label>
                            <input ng-model="newAsgmt.date" id="date" required type="text" class="form-control">
                            <div class="input-group-addon">
                                <span class="glyphicon glyphicon-th"></span>
                            </div>
                        </div>
                        <%-- TIME PICKER --%>
                        <div class="input-group bootstrap-timepicker timepicker asgmt-form">
                            <label for="timepicker1" class="col-2 col-form-label">Due Time</label>
                            <input ng-model="newAsgmt.time" required id="timepicker1" type="text" class="form-control input-small">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
                        </div>
                        <%-- MAX GRADE POSSIBLE --%>
                        <label for="max-grade" class="asgmt-form label">Max Grade</label>
                        <input placeholder="Max Grade" id="max-grade" required type="number" ng-model="newAsgmt.maxGrade" class="form-control">

                        <%-- FILE CHOOSER (NOT REQUIRED) --%>
                        <label class="asgmt-form btn btn-default btn-file">
                            Browse <input type="file" class="hidden" file-model="newAsgmt.file" onchange="$('#upload-file-info').html($(this).val());">
                        </label>
                        <span class='label label-info' id="upload-file-info">No files uploaded</span>
                        <span class="btn btn-danger btn-sm glyphicon glyphicon-remove" ng-if="newAsgmt.file"  ng-click="newAsgmt.file = null;" onclick="$('#upload-file-info').html('No files uploaded');"></span>

                    </div>
                    <%-- SUBMIT FORM & CANCEL BTNS --%>
                    <div class="asgmt-btns">
                        <button type="submit" class="btn btn-default">Submit</button>
                        <button class="btn btn-default" data-toggle="collapse" data-target="#assignmentForm">Cancel</button>
                    </div>
                </form>
            </div>
        </div>

        <a class="list-group-item assignment">
            <c:choose>
                <c:when test="${userType eq 'prof'}">
                    <span class="badge btn-xs col-sm-1 glyphicon glyphicon-trash clickable on-show"></span>
                    <span class="badge btn-xs col-sm-1 glyphicon glyphicon-pencil clickable on-show"></span>
                    <span class="badge  clickable on-show">Cancel</span>
                    <span class="badge clickable on-show">Submit</span>
                </c:when>
            </c:choose>
            <h4>Title</h4>
            <h6>Due Monday, October 31, 2017 at 11:59PM</h6>
            <p>This is the description, if any of the HW</p>
            <p>No files uploaded</p>

        </a>
    </div>
</body>


</html>