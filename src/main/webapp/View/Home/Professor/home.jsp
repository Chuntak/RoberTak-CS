
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8"  %>

<!DOCTYPE html>
<html>
<head>
    <base href="/">
    <!-- Red Robbins -->
    <link rel="icon" type="image/png" href="<c:url value="/images/logo.png" />">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <%--Google Logout--%>
    <meta name="google-signin-scope" content="profile email">
    <meta name="google-signin-client_id" content="1035281966309-b4borhbj8b2i8vljn1q1sndtqg3rpvnq.apps.googleusercontent.com">
    <%--Google Logout End--%>
    <%--Google Logout--%>

    <title>backpack</title>
    <!-- CSS START -->
    <section>
        <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet" type="text/css">
        <link rel="stylesheet" href="<c:url value="/resources/app/css/home.css" />">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
    </section>
    <!-- CSS END -->

</head>

<!--App Content START-->
<body ng-app="homeApp">

<div id="toolbar" ng-controller="homeCtrl">
    <img class="inline_header" src="images/logo_top.png"/>
    <h1 class="text">&nbspbackpack</h1>
    <div class="text-logout">Welcome <%= session.getAttribute("firstName")%> &nbsp
        <%--Sign Out button--%>
        <img src="images/logout.png" ng-controller="homeCtrl" ng-click="signOut()" class="logout clickable" data-toggle="tooltip" title="Sign out" data-placement="bottom"/>
        <form:form id="signOut" name="signOut"  method="GET" action="/signOut">
            <button hidden="hidden" type="submit"></button>
        </form:form>
    </div>
</div>

<div class="content container-fluid app-wrapper">
        <%--COURSE PANE--%>
        <div class="col-md-2 coursePane" ng-controller="courseCtrl">
            <div class="panel panel-default">
                <div class="course-heading">Courses
                    <button id="addCourse" class="btn-success btn btn-default addCourse">
                        <span class="glyphicon glyphicon-plus"></span>
                    </button>
                </div>
                <%--Course Selection (+Modal)--%>
                <div id="courseSelection-prof">
                    <%--AngularJS to dynamically load the courses--%>
                    <div class="list-grouper" ng-repeat="course in courses">
                        <a class="list-group-item"  ng-click="selectCourse(course, $index)" ng-class="{active: $index == selected}">
                            <h4 class="card-title row">
                                <p class="col-sm-9">{{course.prefix}}-{{course.number}}</p>
                                <btn class="btn-xs col-sm-1 glyphicon glyphicon-pencil clickable on-show" ng-click="editCourse(course)">
                                </btn>
                                <btn class="btn-xs col-sm-1 glyphicon glyphicon-trash clickable on-show" ng-click="deleteCourse(course)"style="">
                                </btn>
                                <%--<p class="col-sm-1 hidden"></p>--%>
                            </h4>
                            <h6 class="card-subtitle mb-2 ">{{course.name}}</h6>
                            <h6 class="card-subtitle mb-2 ">{{course.profFirstName}} {{course.profLastName}}</h6>
                            <h6 class="card-subtitle mb-2 ">Course Code: {{course.code}}</h6>
                        </a>
                    </div>

                    <%--ADD COURSE BUTTON--%>
                    <div id="courseAddEdit" class="row container-fluid">

                        <!-- The Modal -->
                        <div id="courseModal" class="modal container-fluid">

                            <!-- Modal content -->
                            <div class="modal-content container-fluid">
                                <span class="close">&times;</span>
                                <h3>Courses</h3>

                                <%--The course prefix and number box--%>
                                <div class="form-inline form-group" id="prefNumDiv">
                                    <div class="col-lg-6">
                                        <input type="text" class="form-control" ng-model="course.prefix" id="coursePrefix" maxlength="8" placeholder="Course Prefix" />
                                    </div>
                                    <div class="col-lg-6">
                                       <input type="text" class="form-control" ng-model="course.number" id="courseNumber" placeholder="Course Number">
                                    </div>
                                </div>

                                <%--The course name--%>
                                <div class="col-lg-12">
                                    <div class="form-group">
                                        <input type="text" class="form-control text-left" ng-model="course.name" id="courseName" maxlength="32" placeholder="Course Name"/>
                                    </div>
                                </div>

                                <%--Semester and Public checkbox--%>
                                <div class="form-inline noFloat" id="semPubDiv">
                                    <div class="col-lg-6">
                                    <select id="semester" class="form-control" ng-model="course.semester">
                                        <option value="" disabled selected>Course Semester</option>

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
                                    </div>

                                    <div class="col-lg-6">
                                        <div class="form-check" id="pubDiv">
                                            <%--<div class="col-xs-11">--%>
                                            <label class="form-check-label" for="public">Make Course Public</label>
                                            <%--</div>--%>
                                            <%--<div class="col-xs-1">--%>
                                            <input class="form-check-input" ng-model="course.public" id="public" type="checkbox"/>
                                            <%--</div>--%>
                                        </div>
                                    </div>
                                </div>

                            <%--Tag Selection and Removal section--%>
                            <div id="tagSection" class="noFloat">
                                <div class="col-lg-10">
                                    <form id="tagAdd" class="form-inline form-group">
                                        <input list="tags"  ng-model="selectedTag" class="form-control" id="tagList" name="tags">
                                        <datalist id="tags">
                                            <option ng-repeat="tag in tagList" value="{{tag.tagName}}">{{tag.tagName}}</option>
                                        </datalist>
                                        <button class="btn" id="addTagBtn" ng-click="addTag()">Add Tag</button>
                                    </form>

                                    <%--Where We add the tag chips--%>
                                    <div id="tagPane" class="noFloat scroll-pane">
                                        <%--The X button should remove the tag instead of hiding it--%>
                                        <div class="chip" ng-repeat="courseTagged in courseTaggedList">
                                            <span class="closebtn" onclick="this.parentElement.style.display='none'">&times;</span>
                                            {{courseTagged}}
                                        </div>

                                    </div>
                                </div>
                            </div>

                                 <%--Submit button to add the course--%>
                                 <div class="form-group" class="noFloat">
                                     <input class="btn btn-primary noFloat" ng-click="updateCourse()" name="submit" id="courseSubmit" cl value="Save Course" type="submit"/>
                                 </div>
                            </div>
                        </div>
                    </div>
                </div>

                <%--SEARCH BAR--%>
                <form class="form-inline global-search" role="form">
                    <div class="form-group">
                        <input type="search" class="form-control" id="search" name="search" placeholder="Enter search terms">
                    </div>
                    <button type="submit" id="search_submit" class="btn btn-default">
                        <span class="glyphicon glyphicon-search"></span>&nbspSearch
                    </button>
                </form>
            </div>
        </div>

    <%--TABS PANE--%>
    <div class="col-md-7 tabPane">
        <div class="panel panel-default">
            <ul class="nav nav-tabs tab-heading" ng-controller="tabsCtrl">
                <li class="tabs clickable" ng-class="tabClass(tab)" ng-repeat="tab in tabs" tab="tab"><a ui-sref="{{tab.state}}" ng-click="setSelectedTab(tab)">{{tab.label}}</a></li>
            </ul>
            <div ui-view></div>
            <div class="panel-body">
                CONTENT
                <%--TAB CONTENT HERE--%>
            </div>
        </div>
    </div>

    <%--FORUM PANE--%>
    <div class="col-md-3 forumPane">
        <div class="panel panel-default">
            <div class="forum-heading">Forum</div>
        </div>
    </div>

    <%--FOOTER--%>
    <div class="footer-content">
        <img src="images/logo_footer.png"> &nbsp
        <span class="text-muted">Copyright &copy 2017 backpack Red Robins. All Rights Reserved.</span>
    </div>
</div>
<!--App Content END-->

</body>

<!-- JavaScripts -->
<section>
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script>
    <script src="https://apis.google.com/js/api:client.js"></script>
    <%--Google Logout End--%>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <!-- App Base -->
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-router/0.4.2/angular-ui-router.min.js"></script>

    <script src="<c:url value="/resources/app/js/professor/home.js" />"  type="text/javascript" ></script>
    <script src="<c:url value="/resources/app/js/professor/course.js" />"  type="text/javascript" ></script>
    <script src="<c:url value="/resources/app/js/professor/tabRoute.js" />"  type="text/javascript" ></script>
    <script src="<c:url value="/resources/app/js/professor/syllabus.js"/>"  type="text/javascript" ></script>
</section>
</html>