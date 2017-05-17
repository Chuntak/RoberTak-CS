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
    <div class="list-group asgmt-wrapper">

    <c:choose>
        <%-- ONLY PROF CAN CREATE ASSIGNMENTS --%>
        <c:when test="${userType eq 'prof' && isOwner eq true}">
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
                            <div class="col-xs-6">
                                <label>Title: </label>
                                <input type="text" class="form-control" ng-model="newAsgmt.title" placeholder="Title" required />
                            </div>

                            <%-- MAX GRADE POSSIBLE --%>
                            <div class="col-xs-6">
                                <label>Maximum Grade: </label>
                                <input placeholder="Maximum Grade" id="max-grade" required type="number" ng-model="newAsgmt.maxGrade" class="form-control">
                            </div>

                            <%-- ASSIGNMENT DESCRIPTION --%>
                            <div class="col-xs-12">
                                <label class="label-padding">Description: </label>
                                <textarea required class="asgmt-form form-control" rows="5" ng-model="newAsgmt.description" placeholder="Write description here"></textarea>
                            </div>

                            <%-- DATE PICKER --%>
                            <div class="col-xs-6">
                                <label class="label-padding">Due Date: </label>
                                <div class="input-group date asgmt-form" id="datepicker" data-provide="datepicker">
                                    <label for="date" class="col-2 col-form-label"></label>
                                    <input ng-model="newAsgmt.date" id="date" required type="text" class="form-control">
                                    <div class="input-group-addon">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                    </div>
                                </div>
                            </div>

                            <%-- TIME PICKER --%>
                            <div class="col-xs-6">
                                <label class="label-padding">Due Time: </label>
                                <div class="input-group bootstrap-timepicker timepicker asgmt-form">
                                    <label for="timepicker1" class="col-2 col-form-label"></label>
                                    <input ng-model="newAsgmt.time" required id="timepicker1" type="text" class="form-control input-small">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
                                </div>
                            </div>

                                <%-- FILE CHOOSER (NOT REQUIRED) --%>
                            <div class="col-xs-4 label-padding">
                                <%-- OPTION TO UPLOAD A FILE --%>
                                <label class="asgmt-form btn btn-default btn-file">Browse
                                    <%-- CHANGE TO UPLOADED FILENAME--%>
                                    <input type="file" class="hidden" file-model="newAsgmt.file" onchange="$('#upload-file-info').html($(this).val());">
                                </label>
                                <%-- DISPLAY WHEN NO FILES ARE UPLOADED --%>
                                <span class='label label-info' id="upload-file-info">No files uploaded</span>
                                <%-- REMOVE FILE UPLOADED --%>
                                <span class="btn btn-danger btn-sm glyphicon glyphicon-remove" ng-if="newAsgmt.file"  ng-click="newAsgmt.file = null;" onclick="$('#upload-file-info').html('No files uploaded');"></span>
                            </div>

                                <%-- CHECKBOX FOR SUBMITTABLE --%>
                            <div class="col-xs-2 label-padding">
                                    <span class="checkbox">
                                        <label class="checkbox"><input type="checkbox" id="checkSub" value="true">Submittable</label>
                                    </span>
                            </div>
                                <%-- SUBMIT FORM & CANCEL BTNS --%>
                            <div class="asgmt-btns">
                                <button type="submit" class="btn btn-default asgmt-btn">Submit</button>
                                <button class="btn btn-default asgmt-btn" data-toggle="collapse" data-target="#assignmentForm" ng-click="cancelAdd(newAsgmt)">Cancel</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </c:when>
    </c:choose>
    <div ng-repeat="asgmt in assignments" class="list-group-item assignment">
        <div id="asgmtViewer{{$index}}">
        <c:choose>
            <%-- ONLY PROFS CAN EDIT/REMOVE--%>
            <c:when test="${userType eq 'prof' && isOwner eq true}">
                <span ng-click="deleteAssignment(asgmt)" class="badge btn-xs col-sm-1 glyphicon glyphicon-trash clickable on-show funcBtn"></span>
                <a id="editAssignment" data-toggle="collapse" data-target="#editAssignment{{$index}}" class="badge btn-md col-sm-1 glyphicon glyphicon-pencil clickable on-show funcBtn" ng-click="initEdit($index,asgmt)"></a>
            </c:when>
        </c:choose>

        <%-- DISPLAY DESCRIPTION FOR ASSIGNMENT--%>
        <h3 ng-bind="asgmt.title"></h3>
        <h6 ng-bind="'Due ' + asgmt.dueDate"></h6>

        <%-- IF FILE EXIST --%>
        <div ng-if="asgmt.hwFileName!=null">
            <label>Download Link: </label> <a href="{{asgmt.hwDownloadLink}}" ng-bind="asgmt.hwFileName"></a>
        </div>
        <%-- IF FILE DOES NOT EXIST --%>
        <div ng-if="asgmt.hwFileName==null"><label>Download Link: </label> No file uploaded</div>
        <p ng-bind="asgmt.description" class="descr-padding"></p>

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

        <%-- EDIT ASSIGNMENT --%>
        <div class="collapse" id="editAssignment{{$index}}">
            <%-- FORM FOR NEW ASSIGNMENT --%>
            <div class="form-group asgmt-form">
                <hr>

                <%-- ASSIGNMENT TITLE --%>
                <div class="col-xs-6">
                    <label>Title: </label><input id="title{{$index}}" type="text" class="form-control" id="title" ng-value="asgmt.title" placeholder="Title" required />
                </div>

                <%-- MAX GRADE POSSIBLE --%>
                <div class="col-xs-6">
                    <label>Maximum Grade:</label><input placeholder="Maximum Grade" required type="number" id="maxGrade{{$index}}" ng-value="asgmt.maxGrade" class="form-control">
                </div>
                <br>
                <%-- ASSIGNMENT DESCRIPTION --%>
                <div class="col-xs-12">
                    <label class="label-padding">Description: </label>
                    <textarea required class="asgmt-form form-control" id="description{{$index}}" rows="5" ng-value="asgmt.description" placeholder="Write description here">{{asgmt.description}}</textarea>
                </div>

                <%-- DATE PICKER --%>
                <div class="col-xs-6">
                    <label class="label-padding">Due Date: </label>
                    <div class="input-group date asgmt-form" id="datepicker{{$index}}" data-provide="datepicker">
                        <label for="date{{$index}}" class="col-2 col-form-label"></label>
                        <input ng-value="asgmt.date" id="date{{$index}}" required type="text" class="form-control">
                        <div class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </div>
                    </div>
                </div>

                <%-- TIME PICKER --%>
                <div class="col-xs-6 select-type">
                    <label class="label-padding">Due Time: </label>
                    <div class="input-group bootstrap-timepicker timepicker asgmt-form">
                        <label for="timepicker1{{$index}}" class="col-2 col-form-label"></label>
                        <input ng-value="asgmt.time" required id="timepicker1{{$index}}" type="text" class="form-control input-small" placeholder="Due Time">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
                    </div>
                </div>

                <%-- FILE CHOOSER (NOT REQUIRED) --%>
                <div class="col-xs-4 label-padding">
                    <%-- IF FILE EXIST --%>
                    <div ng-if="asgmt.hwFileName!=null">
                        <%-- OPTION TO UPLOAD FILE --%>
                        <label class="asgmt-form btn btn-default btn-file">
                            Browse <input type="file" class="hidden" file-model="asgmt.file" onchange="$('#upload-file-info1').html($(this).val());">
                        </label>
                        <span class='label label-info' id="upload-file-info1" ng-model="asgmt.hwFileName">{{asgmt.hwFileName}}</span>
                    </div>
                    <%-- IF FILE DOES NOT EXIST --%>
                    <div ng-if="asgmt.hwFileName==null">
                        <%-- OPTION TO UPLOAD FILE --%>
                        <label class="asgmt-form btn btn-default btn-file">
                            Browse <input type="file" class="hidden" file-model="asgmt.file" onchange="$('#upload-file-info1').html($(this).val());">
                        </label>
                        <span class='label label-info' id="upload-file-info1">No files uploaded</span>
                    </div>

                    <%-- REMOVE BUTTON --%>
                    <span class="btn btn-danger btn-sm glyphicon glyphicon-remove" ng-if="asgmt.file"  ng-click="newAsgmt.file = null;" onclick="$('#upload-file-info1').html('No files uploaded');"></span>
                </div>

                <%-- CHECKBOX FOR SUBMITTABLE --%>
                <div class="col-xs-2 label-padding">
                    <span class="checkbox">
                        <label class="checkbox" ng-model="asgmt.submittable" type="checkbox">
                            <input type="checkbox" id="checkSub{{$index}}" ng-checked="asgmt.submittable">Submittable</label>
                    </span>
                </div>

                <%-- SUBMIT FORM & CANCEL BTNS --%>
                <div class="asgmt-btns">
                    <button type="submit" class="btn btn-default asgmt-btn" data-toggle="collapse" data-target="#editAssignment{{$index}}" ng-click="editAssignment(asgmt, $index)">Submit</button>
                    <button class="btn btn-default asgmt-btn" data-toggle="collapse" data-target="#editAssignment{{$index}}" ng-click="cancelEdit($index)">Cancel</button>
                </div>
            </div>
            </form>
        </div>
        </div>
    </div>
</div>
</body>


</html>