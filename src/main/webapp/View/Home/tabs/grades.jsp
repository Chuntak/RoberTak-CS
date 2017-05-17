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

                    <%--ADD NEW GRADE TASK BUTTON--%>
                <div class="list-group-item-success">
                    <h4 class="panel-title" id="panel">
                        <a id="createGradable" data-toggle="collapse" data-target="#gradableForm">Add New Grade Task</a>
                    </h4>
                </div>

                <%--ADD NEW GRADE TASK FORM--%>
                <div class="collapse" id="gradableForm">
                    <%--<form ng-submit="updateGradable(gradable)">--%>
                    <div class="form-group gradable-form">
                            <%-- GRADABLE TITLE --%>
                        <div class="col-xs-6">
                            <label>Title: </label>
                            <input type="text" class="form-control" ng-model="gradable.title" placeholder="Title" required />
                        </div>
                        <div class="col-xs-6">
                            <label>Maximum Grade: </label>
                            <input type="number" class="form-control" ng-model="gradable.maxGrade" placeholder="Maximum Grade" required />
                        </div>
                        <div class="col-lg-12">
                            <label class="label-padding">Description: </label>
                            <textarea type="text" class="form-control" rows="5" ng-model="gradable.description" placeholder=" Write description here" required></textarea>
                        </div>
                    </div>

                        <%-- DATE PICKER --%>
                    <div class="col-xs-4">
                        <label class="label-padding">Due Date: </label>
                        <div class="input-group date gradable-form" id="datepicker" data-provide="datepicker">
                            <label for="date" class="col-2 col-form-label"></label>
                            <input ng-model="gradable.date" id="date" required type="text" class="form-control" placeholder="Due Date">
                            <div class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                            </div>
                        </div>
                    </div>

                        <%-- TIME PICKER --%>
                    <div class="col-xs-4">
                        <label class="label-padding">Due Time: </label>
                        <div class="input-group bootstrap-timepicker timepicker gradable-form">
                            <label for="timepicker1" class="col-2 col-form-label"></label>
                            <input ng-model="gradable.time" required id="timepicker1" type="text" class="form-control input-small" placeholder="Due Time">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
                        </div>
                    </div>

                        <%-- TYPE PICKER --%>
                    <div class="col-xs-4 select-type">
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
                    <div class="col-xs-12 gradable-btns">
                        <p><input type="submit" class="btn btn-default" ng-click="updateGradable(gradable)"/>
                            <button class="btn btn-default" data-toggle="collapse" data-target="#gradableForm">Cancel</button></p>
                    </div>
                </div>
            </div>
        </c:when>
    </c:choose>

    <div class="panel-group" id="gradeAccordion">
        <div ng-repeat="gradable in gradables" class="panel list-group-item gradable">
            <div id="gradableViewer{{$index}}">

                <%-- ONLY PROFESSOR CAN EDIT AND SEE THIS FUNCTIONALITY --%>
                <c:choose>
                    <c:when test="${userType eq 'prof'}">
                        <btn class="badge btn-md col-sm-1 glyphicon glyphicon-trash clickable on-show" ng-click="deleteGradable(gradable)"></btn>
                        <a id="editGradable" data-toggle="collapse" data-target="#editGradable{{$index}}" class="badge btn-md col-sm-1 glyphicon glyphicon-pencil clickable on-show" ng-click="initEdit($index, gradable)"></a>
                    </c:when>
                </c:choose>

                <%-- DISPLAYS DESCRIPTION OF GRADE TASK --%>
                <h3><a ng-bind="gradable.title" ng-click="getGrades(gradable.id, $index)" data-toggle="collapse" data-target="{{'#grid'+$index}}" data-parent="#gradeAccordion" ></a></h3>
                <h6 ng-bind="'Due: ' + gradable.dueDate"></h6>
                <h6 ng-bind="'Maximum Grade: ' + gradable.maxGrade"></h6>
                <h5 ng-bind="gradable.description" class="description-color"></h5>

                <%-- KENDO --%>
                <div id="{{ 'grid' + $index }}" class="panel-collapse collapse">
                    <div class="panel-body">
                        <h3>Grades</h3>
                        <kendo-grid id="{{ 'kendogrid' + $index }}" options="mainGridOptions"></kendo-grid>
                    </div>
                </div>

                <%-- EDIT GRADABLE --%>
                <div class="collapse" id="editGradable{{$index}}" >
                    <div>
                        <div class="form-group gradable-form">
                            <hr>

                            <%-- GRADABLE TITLE --%>
                            <div class="col-xs-6">
                                <label class="label-padding">Title: </label>
                                <input id="title{{$index}}" type="text" class="form-control" ng-value="gradable.title" placeholder="Title" required />
                            </div>

                            <%-- GRADABLE MAX GRADE VALUE --%>
                            <div class="col-xs-6">
                                <label class="label-padding">Maximum Grade: </label>
                                <input id="maxGrade{{$index}}" type="number" class="form-control" ng-value="gradable.maxGrade" placeholder="Maximum Grade" required />
                            </div>

                            <%-- GRADABLE DESCRIPTION --%>
                            <div class="col-xs-12">
                                <label class="label-padding">Description: </label>
                                <textarea id="description{{$index}}" row="5" type="text" ng-value="gradable.description" class="form-control" placeholder="Write description here">{{gradable.description}}</textarea>
                            </div>


                            <%-- DATE PICKER --%>
                            <div class="col-xs-4">
                                <label class="label-padding">Due Date: </label>
                                <div class="input-group date gradable-form" id="datepicker{{$index}}" data-provide="datepicker">
                                    <label for="date{{$index}}" class="col-2 col-form-label"></label>
                                    <input ng-value="gradable.date" id="date{{$index}}" required type="text" class="form-control" placeholder="Due Date">
                                    <div class="input-group-addon">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                    </div>
                                </div>
                            </div>

                            <%-- TIME PICKER --%>
                            <div class="col-xs-4">
                                <label class="label-padding">Due Time: </label>
                                <div class="input-group bootstrap-timepicker gradable-form">
                                    <label for="timepicker1{{$index}}" class="col-2 col-form-label"></label>
                                    <input ng-value="gradable.time" required id="timepicker1{{$index}}" type="text" class="form-control input-small" placeholder="Due Time">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
                                </div>
                            </div>

                            <%-- TYPE SELECTOR --%>
                            <div class="col-xs-4">
                                <form class="form-inline select-type">
                                    <label class="mr-sm-2" for="inlineFormCustomSelect1">Choose Type:  </label>
                                    <select class="custom-select mb-2 mr-sm-2 mb-sm-0" id="inlineFormCustomSelect1" id="type{{$index}}" ng-value="gradable.gradableType">
                                        <option selected value="att">Attendance</option>
                                        <option value="exam">Exam</option>
                                        <option value="ec" >Extra Credit</option>
                                        <option value="other">Other</option>
                                    </select>
                                </form>
                            </div>

                            <%-- SUBMIT FORM & CANCEL BTNS --%>
                            <div class="gradable-btns">
                                <button type="submit" class="btn btn-default" ng-click="editGradable(gradable, $index)">Save Changes</button>
                                <button class="btn btn-default" data-toggle="collapse" data-target="#editGradable{{$index}}" ng-click="cancelEdit($index)">Cancel</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>


</html>