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
    <link rel="stylesheet" href="<c:url value="/resources/app/css/documents.css" />">

    <link rel="stylesheet" href="<c:url value="/resources/app/css/imported/bootstrap-datepicker.css" />">
    <link rel="stylesheet/less" href="<c:url value="/resources/app/css/imported/timepicker.less" />">

    <!--KENDO UI -->
    <link rel="stylesheet" href="https://kendo.cdn.telerik.com/2017.1.118/styles/kendo.common-material.min.css" />
    <link rel="stylesheet" href="https://kendo.cdn.telerik.com/2017.1.118/styles/kendo.material.min.css" />
    <link rel="stylesheet" href="https://kendo.cdn.telerik.com/2017.1.118/styles/kendo.material.mobile.min.css" />


    <title>Grades</title>
</head>
<body>
<div ng-controller="gradesCtrl">
    <c:choose>
        <%--IF PROFESSOR, APPLY THIS FUNCTIONALITY--%>
        <c:when test="${userType eq 'prof'}">
            <div class="gradable-create">
                <div class="list-group-item-success">
                    <h4 class="panel-title" id="panel">
                        <a id="createGradable" data-toggle="collapse" data-target="#gradableForm">Add New Grade Task</a>
                    </h4>
                </div>
                <div class="collapse" id="gradableForm">
                        <%-- FORM FOR NEW GRADABLE --%>
                    <div>
                        <div class="form-group gradable-form">
                                <%-- GRADABLE TITLE --%>
                            <div class="col-md-6">
                                <input type="text" class="form-control" ng-model="gradable.title" placeholder="Title" required />
                            </div>
                            <div class="col-lg-6">
                                <input type="text" class="form-control" ng-model="gradable.maxGrade" placeholder="Maximumm Grade" required />
                            </div>
                            <div class="col-md-12">
                                <textarea type="text" ng-model="gradable.description" class="description-edit" placeholder=" Description"></textarea>
                            </div>
                        </div>

                            <%-- DATE PICKER --%>
                        <div class="col-md-4">
                            <div class="input-group date gradable-form" id="datepicker" data-provide="datepicker">
                                <label for="date" class="col-2 col-form-label"></label>
                                <input ng-model="gradable.date" id="date" required type="text" class="form-control" placeholder="Due Date">
                                <div class="input-group-addon">
                                    <span class="glyphicon glyphicon-th"></span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <%-- TIME PICKER --%>
                            <div class="input-group bootstrap-timepicker timepicker gradable-form">
                                <label for="timepicker1" class="col-2 col-form-label"></label>
                                <input ng-model="gradable.time" required id="timepicker1" type="text" class="form-control input-small" placeholder="Due Time">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
                            </div>
                        </div>
                        <div class="col-md-4 select-type">
                            <form class="form-inline">
                                <label class="mr-sm-2" for="inlineFormCustomSelect">Choose Type:  </label>
                                <select class="custom-select mb-2 mr-sm-2 mb-sm-0" id="inlineFormCustomSelect" ng-model="gradable.gradableType">
                                    <option selected value="att">Attendance</option>
                                    <option value="exam">Exam</option>
                                    <option value="ec" >Extra Credit</option>
                                    <option value="other">Other</option>
                                </select>
                            </form>
                        </div>

                            <%-- SUBMIT FORM & CANCEL BTNS --%>
                        <div class="col-md-12 gradable-btns">
                            <p><button type="submit" class="btn btn-default" ng-click="updateGradable(gradable)" data-toggle="collapse" data-target="#gradableForm">Submit</button>
                                <button class="btn btn-default" data-toggle="collapse" data-target="#gradableForm">Cancel</button></p>
                        </div>
                    </div>
                </div>
            </div>
        </c:when>
    </c:choose>

    <%-- Load the Grade Titles and descriptions --%>
    <div ng-repeat="gradable in gradables">

    <%--<script type="text/ng-template" id="display" >--%>
        <div class="list-group-item gradable">
            <%--<span ng-click="deleteAssignment(gradable)" class="btn-xs col-sm-1 glyphicon glyphicon-trash clickable on-show"></span>--%>
            <%--<span class="badge btn-xs col-sm-1 glyphicon glyphicon-pencil clickable on-show"></span>--%>
            <btn class="badge btn-md col-sm-1 glyphicon glyphicon-trash clickable on-show" ng-click="deleteGradable(gradable)"></btn>
            <a id="editGradable" data-toggle="collapse" data-target="#editGradable{{$index}}" class="badge btn-md col-sm-1 glyphicon glyphicon-pencil clickable on-show"></a>
            <h3 ng-bind="gradable.title"></h3>
            <h5 ng-bind="gradable.description"></h5>
            <h5 ng-bind="'Maximum Grade: ' + gradable.maxGrade"></h5>
            <h6 ng-bind="'Due: ' + gradable.dueDate"></h6>

                <div class="collapse" id="editGradable{{$index}}">
                <div>
                    <div class="form-group gradable-form">
                        <%-- GRADABLE TITLE --%>
                        <div class="col-md-6">
                            <input id="title{{$index}}" type="text" class="form-control" value="{{gradable.title}}" placeholder="Title" required />
                        </div>
                        <div class="col-md-6">
                            <input type="text" class="form-control" ng-model="gradable.maxGrade" placeholder="Maximumm Grade" required />
                        </div>
                        <div class="col-md-12">
                            <textarea type="text" ng-model="gradable.description" class="description-edit"></textarea>
                        </div>
                    </div>

                    <%-- DATE PICKER --%>
                    <div class="col-md-4">
                        <div class="input-group date gradable-form" id="datepicker1" data-provide="datepicker1">
                            <label for="date1" class="col-2 col-form-label"></label>
                            <input ng-model="gradable.date" id="date1" required type="text" class="form-control" placeholder="Due Date">
                            <div class="input-group-addon">
                                <span class="glyphicon glyphicon-th"></span>
                            </div>
                        </div>
                    </div>
                    <%-- TIME PICKER --%>
                    <div class="col-md-4">
                    <div class="input-group bootstrap-timepicker timepicker gradable-form">
                        <label for="timepicker2" class="col-2 col-form-label"></label>
                        <input ng-model="gradable.time" required id="timepicker2" type="text" class="form-control input-small" placeholder="Due Time">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
                    </div>
                    </div>

                    <%-- TYPE SELECTOR --%>
                    <div class="col-md-4">
                    <form class="form-inline select-type">
                        <label class="mr-sm-2" for="inlineFormCustomSelect1">Choose Type:  </label>
                        <select class="custom-select mb-2 mr-sm-2 mb-sm-0" id="inlineFormCustomSelect1" ng-model="gradable.gradableType">
                            <option selected value="att">Attendance</option>
                            <option value="exam">Exam</option>
                            <option value="ec" >Extra Credit</option>
                            <option value="other">Other</option>
                        </select>
                    </form>
                    </div>

                    <%-- SUBMIT FORM & CANCEL BTNS --%>
                    <div class="gradable-btns">
                        <button type="submit" class="btn btn-default" ng-click="editGradable(gradable, $index)">Submit</button>
                        <button class="btn btn-default" data-toggle="collapse" data-target="#editGradable{{$index}}">Cancel</button>

                    </div>
                </div>
            </div>

        </div>
    </div>
    <div id="items" class="panel-collapse collapse">
        <h3>Items</h3>

        <kendo-grid id="grid" options="mainGridOptions">

        </kendo-grid>

    </div>



</div>
</body>


</html>