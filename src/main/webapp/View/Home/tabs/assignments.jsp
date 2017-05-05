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

        <c:choose>
            <%-- ONLY PROF CAN CREATE ASSIGNMENTS --%>
            <c:when test="${userType eq 'prof'}">
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
                                <div class="col-xs-3 input-group date asgmt-form" id="datepicker" data-provide="datepicker">
                                    <label for="date" class="col-2 col-form-label">Due Date</label>
                                    <input ng-model="newAsgmt.date" id="date" required type="text" class="form-control">
                                    <div class="input-group-addon">
                                        <span class="glyphicon glyphicon-th"></span>
                                    </div>
                                </div>
                                <%-- TIME PICKER --%>
                                <div class="col-xs-3 input-group bootstrap-timepicker timepicker asgmt-form">
                                    <label for="timepicker1" class="col-2 col-form-label">Due Time</label>
                                    <input ng-model="newAsgmt.time" required id="timepicker1" type="text" class="form-control input-small">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
                                </div>
                                <%-- MAX GRADE POSSIBLE --%>
                                <label for="max-grade" class="asgmt-form label">Max Grade</label>
                                <input placeholder="Max Grade" id="max-grade" required type="number" ng-model="newAsgmt.maxGrade" class="form-control">
                                    <span class="checkbox">
                                        <label class="checkbox"><input checked type="checkbox" value="true">Submittable</label>
                                    </span>
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
            </c:when>
        </c:choose>
        <div ng-repeat="asgmt in assignments" class="list-group-item assignment">
            <c:choose>
                <%-- ONLY PROFS CAN EDIT/REMOVE--%>
                <c:when test="${userType eq 'prof'}">
                    <span ng-click="deleteAssignment(asgmt)" class="badge btn-xs col-sm-1 glyphicon glyphicon-trash clickable on-show"></span>
                    <span class="badge btn-xs col-sm-1 glyphicon glyphicon-pencil clickable on-show"></span>
                    <%--<span class="badge  clickable on-show">Cancel</span>--%>
                    <%--<span class="badge clickable on-show">Submit</span>--%>
                </c:when>
            </c:choose>
            <h4 ng-bind="asgmt.title"></h4>
            <h6 ng-bind="'Due ' + asgmt.dueDate"></h6>
            <p ng-bind="asgmt.description"></p>
            <a href="'#' + asgmt.hwDownloadLink" ng-bind="asgmt.hwBlobName">Download Me :-)</a>
            <%--<p ng-if="asgmt."></p>--%>
            <c:choose>
                <%-- ONLY STUDENTS CAN SUBMIT HW --%>
                <c:when test="${userType eq 'stud'}">
                    <form id="student-submission" ng-submit="uploadSubmission(asgmt)">
                        <label class="asgmt-form btn btn-default btn-file">
                            Browse <input type="file" class="hidden" file-model="asgmt.submission" onchange="$('#submit-file-info').html($(this).val());">
                        </label>
                        <span class='label label-info' id="submit-file-info">{{ asgmt.submissionBlobName || 'No file uploaded'}}</span>
                        <span class="btn btn-danger btn-sm glyphicon glyphicon-remove" ng-if="asgmt.submission"  ng-click="asgmt.submission = null;" onclick="$('#submit-file-info').html('No file uploaded');"></span>
                        <button type="submit" class="btn btn-primary btn-sm glyphicon glyphicon-upload" ng-if="asgmt.submission" ></button>
                    </form>
                </c:when>
            </c:choose>

        </div>
    </div>
</body>


</html>