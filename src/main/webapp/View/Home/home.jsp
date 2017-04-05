
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8"  %>

<!-- ADDED BY ROBERT FROM CLANS -->

<!DOCTYPE html>
<html class="container-fluid">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <%--Google Logout--%>
    <meta name="google-signin-scope" content="profile email">
    <meta name="google-signin-client_id" content="858023592805-rlit5sgi3a4mplhq1fgkk58522brusjo.apps.googleusercontent.com">
    <%--Google Logout End--%>
    <%--Google Logout--%>
    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="<c:url value="/resources/app/css/home.css" />">

    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script>
    <!-- Bootst<link rel="stylesheet" media="screen" href="<c:url value="/resources/library-vendor/bootstrap/css/bootstrap.min.css" />" >
rap -->

    <script src="https://apis.google.com/js/api:client.js"></script>
    <script>
        function onLoad() {
            gapi.load('auth2', function() {
                debugger;
                gapi.auth2.init();
            });
        }
    </script>
    <%--Google Logout End--%>

    <!-- JavaScripts -->
    <section>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

        <!-- Google Code Prettify -->
        <script src="<c:url value="/resources/library-vendor/google-code-prettify/prettify.js" />" type="text/javascript"></script>

        <!-- App Base -->
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
        <script src="<c:url value="/resources/app/js/course.js" />"  type="text/javascript" ></script>
        <script src="<c:url value="/resources/app/js/home.js" />"  type="text/javascript" ></script>
        <!--KENDO UI -->
        <!-- THE FOLLOWING SCRIPT (KENDO) REQUIRES JQUERY & ANGULARJS-->
        <!--<script src="https://kendo.cdn.telerik.com/2017.1.118/js/jquery.min.js"></script>-->
        <!--<script src="https://kendo.cdn.telerik.com/2017.1.118/js/angular.min.js"></script>-->
        <script src="https://kendo.cdn.telerik.com/2017.1.118/js/kendo.all.min.js"></script>
        <!--KENDO UI-->

        <!-- CSS START -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <!-- CSS END -->
    </section>

    <title>Robertak-CS</title>

</head>

<!--App Content START-->
<body ng-app="homeInApp" ng-controller="homeCtrl">

<div>
    <img class="inline_header" src="images/logo.png" style="height:60px;width:75px;margin-top:10px;"/>
    <h2 class="text">&nbspRoberTak-CS</h2>
    <div class="text-logout">Welcome <%= session.getAttribute("firstName")%> &nbsp
        <%--Sign Out button--%>
        <img src="images/logout.png" ng-controller="homeCtrl" ng-click="signOut()" class="btn btn-default" data-toggle="tooltip" title="Sign out" data-placement="bottom"/>
        <form:form id="signOut" name="signOut"  method="GET" action="/signOut">
            <button hidden="hidden" type="submit"></button>
        </form:form>
    </div>
</div>

<div class="app-wrapper row container-fluid" id="wrapper">
    <div id="coursePane" class="col-xs-2 container-fluid">
        <div id="courseLabel" class="row container-fluid">
            <div><h4 class="container-fluid">Courses</h4></div>
            <div id="courseSelection" class="row container-fluid pre-scrollable">
                <%--AngularJS to dynamically load the courses--%>
                <ul>
                    <li ng-repeat="course in courses">
                        <a href={{course.courseName}}></a>
                    </li>
                </ul>
            </div>

            <%--ADD COURSE BUTTON--%>
            <div id="courseAddEdit" class="row container-fluid">
                <button id="addCourse">Add Course</button>
                <!-- The Modal -->
                <div id="courseModal" class="modal">

                    <!-- Modal content -->
                    <div class="modal-content" ng-controller="courseCtrl">
                        <span class="close">&times;</span>
                        <div class="form-group">
                            <p>
                                <label for="coursePrefix">Course Prefix:</label>
                                <input type="text" ng-model="course.prefix" id="coursePrefix" maxlength="8" placeholder="ie:CSE" />
                            </p>
                        </div>

                        <div class="form-group">
                            <p>
                                <label for="courseNumber">Course Number:</label>
                                <input type="text" ng-model="course.number" id="courseNumber" maxlength="4" placeholder="ie:308"/>
                            </p>
                        </div>

                        <div class="form-group">
                            <p>
                                <label for="courseName">Course Name:</label>
                                <input type="text" ng-model="course.name" id="courseName" maxlength="32" placeholder="ie:Intro Computer"/>
                            </p>
                        </div>

                        <div class="form-group">
                            <p>
                                <label for="semester">Course Semester:</label>
                                <select id="semester" ng-model="course.semester">
                                    <option value="sp17">Spring 2017</option>
                                    <option value="su17">Summer 2017</option>
                                    <option value="fa17">Fall 2017</option>
                                    <option value="wi17">Winter 2017</option>

                                    <option value="sp18">Spring 2018</option>
                                    <option value="su18">Summer 2018</option>
                                    <option value="fa18">Fall 2018</option>
                                    <option value="wi18">Winter 2018</option>

                                    <option value="sp19">Spring 2019</option>
                                    <option value="su19">Summer 2019</option>
                                    <option value="fa19">Fall 2019</option>
                                    <option value="wi19">Winter 2019</option>

                                    <option value="sp20">Spring 2020</option>
                                    <option value="su20">Summer 2020</option>
                                    <option value="fa20">Fall 2020</option>
                                    <option value="wi20">Winter 2020</option>
                                </select>
                            </p>
                        </div>

                        <div class="form-check">
                            <label class="form-check-label" for="public">Make Course Public</label>
                            <input class="form-check-input" ng-model="course.public" id="public" type="checkbox"/>
                        </div>

                        <br><br>

                        <div>
                            <input class="btn btn-primary" ng-click="addCourse()" name="submit" id="courseSubmit" value="Add Course" type="submit"/>
                        </div>
                    </div>

                </div>
            </div>


            <div id="courseRegister" class="row container-fluid">
                <input type="text" placeholder="Enter Course Code">
                <button> Register Course </button>
            </div>
            <div id="courseSearch" class="row container-fluid">
                <input type="text" name="search" placeholder="Search..">
            </div>
        </div>
    </div>
    <div class="col-xs-7 container-fluid" id="tabPane">
        Tabs
    </div>

    <div class="col-xs-3 container-fluid" id="forumPane">
        <div id="forumLabel" class="row container-fluid">
            <div>
                <h4 class="container-fluid">Forum</h4>
            </div>
        </div>
    </div>

</div>
<!--App Content END-->

</body>
</html>