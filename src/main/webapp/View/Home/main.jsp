
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
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script>
    <!-- Bootst<link rel="stylesheet" media="screen" href="<c:url value="/resources/library-vendor/bootstrap/css/bootstrap.min.css" />" >
rap -->

    <script src="https://apis.google.com/js/api:client.js"></script>

    <script>
        function signOut() {
            var auth2 = gapi.auth2.getAuthInstance();
            auth2.signOut().then(function () {
//            document.getElementById('name').innerText = "Signed in: " +
//                googleUser.getBasicProfile().getName();
                console.log('User signed out.');
                document.forms["loadPage_index"].submit();
                alert("Signed Out");
            }, function(error) {
                alert(JSON.stringify(error, undefined, 2));
            });
        }

        function onLoad() {
            gapi.load('auth2', function() {
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
        <script src="<c:url value="/resources/app/js/ang_test.js" />"  type="text/javascript"></script>

        <!--KENDO UI -->
        <!-- THE FOLLOWING SCRIPT (KENDO) REQUIRES JQUERY & ANGULARJS-->
        <!--<script src="https://kendo.cdn.telerik.com/2017.1.118/js/jquery.min.js"></script>-->
        <!--<script src="https://kendo.cdn.telerik.com/2017.1.118/js/angular.min.js"></script>-->
        <script src="https://kendo.cdn.telerik.com/2017.1.118/js/kendo.all.min.js"></script>
        <!--KENDO UI-->
    </section>


    <title>Robertak-CS</title>

</head>
<body ng-app="myApp" ng-controller="myCtrl" class="container-fluid">

<!--App Content START-->
<h1><span class="label label-default">Welcome {{userName}}</span></h1>
<div class="app-wrapper row container-fluid" id="wrapper">

    <div id="coursePane" class="col-xs-2 container-fluid">
        <div id="courseLabel" class="row container-fluid">
            <div>
                <h4 class="container-fluid">Courses</h4>
            </div>
            <div id="courseSelection" class="row container-fluid pre-scrollable">
                <%--AngularJS to dynamically load the courses--%>
                <ul>
                    <li ng-repeat="course in courses">
                        <a href={{course.courseName}}></a>
                    </li>
                </ul>


            </div>
            <div id="courseAddEdit" class="row container-fluid">
                <button id="addCourse">Add Course</button>
                <!-- The Modal -->
                <div id="courseModal" class="modal">

                    <!-- Modal content -->
                    <div class="modal-content">
                        <span class="close">&times;</span>
                        <form method="GET" id="courseForm">

                            <div class="form-group">
                                <p>
                                    <label for="coursePrefix">Course Prefix:</label>
                                    <input path="coursePrefix" type="text" id="coursePrefix" maxlength="8" placeholder="ie:CSE" >
                                </p>
                            </div>

                            <div class="form-group">
                                <p>
                                    <label for="courseNumber">Course Number:</label>
                                    <input path="courseNumber" type="text" id="courseNumber" maxlength="4" placeholder="ie:308">
                                </p>
                            </div>

                            <div class="form-group">
                                <p>
                                    <label for="courseName">Course Name:</label>
                                    <input path="courseName" type="text" id="courseName" maxlength="32" placeholder="ie:Intro Computer">
                                </p>
                            </div>

                            <div class="form-group">
                                <p>
                                    <label for="semester">Course Semester:</label>
                                    <select path="semester" form="courseForm" id="semester">
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
                                    <input class="form-check-input" id="public" type="checkbox">
                            </div>

                            <br><br>

                            <div>
                                <input class="btn btn-primary" name="submit" id="courseSubmit" value="addCourse" type="submit"></input>
                            </div>
                        </form>
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
<!--App Content END--

<!-- CSS START -->
<section>
    <link rel="stylesheet" href="<c:url value="/resources/app/css/todolist_maker.css" />">

    <link rel="stylesheet" href="<c:url value="/resources/app/css/main.css" />">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <!--KENDO UI -->
    <link rel="stylesheet" href="https://kendo.cdn.telerik.com/2017.1.118/styles/kendo.common-material.min.css" />
    <link rel="stylesheet" href="https://kendo.cdn.telerik.com/2017.1.118/styles/kendo.material.min.css" />
    <link rel="stylesheet" href="https://kendo.cdn.telerik.com/2017.1.118/styles/kendo.material.mobile.min.css" />

</section>
<!-- CSS END -->

<script src="<c:url value="/resources/app/js/course.js" />"  type="text/javascript" ></script>

</body>
</html>