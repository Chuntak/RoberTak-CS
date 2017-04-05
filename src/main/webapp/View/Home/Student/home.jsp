
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8"  %>

<!-- ADDED BY ROBERT FROM CLANS -->

<!DOCTYPE html>
<html>
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

    <title>Robertak-CS</title>
    <!-- CSS START -->
    <section>
        <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet" type="text/css">
        <link rel="stylesheet" href="<c:url value="/resources/app/css/home.css" />">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    </section>
    <!-- CSS END -->
    <!-- Red Robbins -->
    <link rel="icon" type="image/png" href="<c:url value="/images/logo.png"/>">
</head>

<!--App Content START-->
<body ng-app="homeApp">

<div id="toolbar" ng-controller="homeCtrl">
    <img class="inline_header" src="images/logo.png" style="height:40px;width:55px;margin-top:-5px;"/>
    <h1 class="text">&nbspRoberTak-CS</h1>
    <div class="text-logout">Welcome <%= session.getAttribute("firstName")%> &nbsp
        <%--Sign Out button--%>
        <img src="images/logout.png" ng-controller="homeCtrl" ng-click="signOut()" class="logout" data-toggle="tooltip" title="Sign out" data-placement="bottom"/>
        <form:form id="signOut" name="signOut"  method="GET" action="/signOut">
            <button hidden="hidden" type="submit"></button>
        </form:form>
    </div>
</div>

<div class="content app-wrapper">
    <div class="container-fluid">
        <%--COURSE PANE--%>
        <div class="col-md-2 coursePane" ng-controller="courseCtrl">
            <div class="panel panel-default">
                <div class="panel-heading">Courses
                </div>
                <div class="panel-body">
                    <%--<div id="courseSelection" class="row container-fluid pre-scrollable">--%>
                    <%--AngularJS to dynamically load the courses--%>
                    <ul>
                        <li ng-repeat="course in courses">
                            <a href={{course.courseName}}></a>
                        </li>
                    </ul>
                </div>

                <%--Enroll to CourseCOde--%>
                <form class="form-inline">
                    <div id="courseRegister" class="row container-fluid">
                        <div class="form-group">
                            <input type="enroll" class="form-control" id="enroll" name="enroll" placeholder="Enter Course Code">
                        </div>
                        <button class="btn btn-default" ng-click="enrollCourse()"> Register Course </button>
                    </div>
                </form>


            </div>
        </div>

        <%--TABS PANE--%>
        <div class="col-md-7 tabPane">
            <div class="panel panel-default">
                <div class="panel-heading">Tabs

                    <%--TAB SWITCHING HERE--%>
                    <ui-view></ui-view> </div>

                <div class="panel-body">
                    CONTENT
                    <%--TAB CONTENT HERE--%>
                </div>
            </div>
        </div>

        <%--FORUM PANE--%>
        <div class="col-md-3 forumPane">
            <div class="panel panel-default">
                <div class="panel-heading">Forum</div>
                <div class="panel-body">CONTENT</div>
            </div>
        </div>
    </div>
    <%--FOOTER--%>
    <div class="container-fluid panel-footer">
        <span class="text-muted">&copy 2017 RoberTak-CS Red Robins All Rights Reserved.</span>
    </div>
</div>
<!--App Content END-->

</body>

<!-- JavaScripts -->
<section>
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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <!-- App Base -->
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
    <script src="<c:url value="/resources/app/js/home.js" />"  type="text/javascript" ></script>
    <script src="<c:url value="/resources/app/js/course.js" />"  type="text/javascript" ></script>
</section>
</html>