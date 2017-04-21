
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
        <link rel="stylesheet" href="<c:url value="/resources/app/css/forum.css" />">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
        <c:choose>
            <c:when test="$(userType eq 'prof')">
                <link rel="stylesheet" href="<c:url value="/resources/app/css/professor.css" />">
            </c:when>
            <c:when test="$(userType eq 'stud')">
                <link rel="stylesheet" href="<c:url value="/resources/app/css/student.css" />">
            </c:when>
        </c:choose>
    </section>
    <!-- CSS END -->
</head>

<!--App Content START-->
<body ng-app="homeApp">

<div id="toolbar" ng-controller="homeCtrl">
    <img class="inline_header" src="images/logo_top.png"/>
    <h1 class="text">&nbspbackpack</h1>
    <div class="text-logout">Welcome <c:out value="${firstName}"/> &nbsp
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
            <%--Course Heading--%>
            <c:choose>
                <c:when test="${userType eq 'prof'}">
                    <%--Add Course--%>
                    <div class="course-heading">Courses
                        <button id="addCourse" class="btn-success btn btn-default addCourse">
                            <span class="glyphicon glyphicon-plus"></span>
                        </button>
                    </div>
                </c:when>
                <c:when test="${userType eq 'stud'}">
                    <%--Enroll--%>
                    <div id="courseRegister" class="row container-fluid">
                        <div class="input-group">
                                <%--ENROLL TO COURSE BTN--%>
                            <input type="text" autocomplate="off" class="form-ctrl course-box" id="enroll" ng-model="course.code" name="enroll" placeholder="Course Code">
                            <span class="input-group-btn">
                                    <button class="btn btn-success glyphicon glyphicon-plus course-btn" ng-click="enrollCourse()" type="button"></button>
                                </span>
                        </div>
                    </div>
                </c:when>
            </c:choose>
            <%--Course Selection (+Modal)--%>
            <div id="courseSelection">
                <%--AngularJS to dynamically load the courses--%>
                <div class="list-grouper" ng-repeat="course in courses">
                    <a class="list-group-item"  ng-click="selectCourse(course, $index)" ng-class="{active: $index == selected}">
                        <h4 class="card-title row">
                            <p class="col-sm-9" ng-bind="(course.prefix)+-+(course.number)"></p>
                            <c:choose>
                                <c:when test="${userType eq 'prof'}">
                                    <btn class="btn-xs col-sm-1 glyphicon glyphicon-pencil clickable on-show" ng-click="editCourse(course)">
                                    </btn>
                                    <btn class="btn-xs col-sm-1 glyphicon glyphicon-trash clickable on-show" ng-click="deleteCourse(course)"style="">
                                    </btn>
                                </c:when>
                            </c:choose>
                            <%--<p class="col-sm-1 hidden"></p>--%>
                        </h4>

                        <h6 class="card-subtitle mb-2" ng-bind="course.name"></h6>

                        <h6 class="card-subtitle mb-2" ng-bind="(course.semester)+' '+(course.ano)"></h6>
                        <c:choose>
                            <c:when test="${userType eq 'prof'}">
                                <h6 class="card-subtitle mb-2" ng-bind="'Course Code: '+ (course.code)"></h6>
                            </c:when>
                            <c:when test="${userType eq 'stud'}">
                                <h6 class="card-subtitle mb-2" ng-bind="(course.profFirstName)+' '+(course.profLastName)"></h6>
                            </c:when>
                        </c:choose>
                    </a>
                </div>

                <%--ADD COURSE BUTTON--%>
                <c:choose>
                    <c:when test="${userType eq 'prof'}">
                        <!-- The Modal -->
                        <div id="courseModal" class="modal container-fluid">

                            <!-- Modal content -->
                            <div class="modal-content">
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
                                    <div class="col-lg-3">
                                        <select id="semester" class="form-control" ng-model="course.semester">
                                            <option value="" disabled selected>Semester</option>
                                            <option value="Spring">Spring</option>
                                            <option value="Summer">Summer</option>
                                            <option value="Fall">Fall</option>
                                            <option value="Winter">Winter</option>
                                        </select>
                                    </div>
                                    <div class="col-lg-3">
                                        <select id="ano" class="form-control" ng-model="course.ano">
                                            <option value="" disabled selected>Year</option>
                                            <option value="2017">2017</option>
                                            <option value="2018">2018</option>
                                            <option value="2019">2019</option>
                                            <option value="2020">2020</option>
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
                                    <div class="col-lg-12">
                                        <form id="tagAdd" class="form-inline form-group">
                                            <input list="tags"  ng-model="selectedTag" class="form-control" id="tagList" name="tags">
                                            <datalist id="tags">
                                                <option ng-repeat="tag in tagList" ng-bind="tag.tagName" value="{{tag.tagName}}"></option>
                                            </datalist>
                                            <button class="btn" id="addTagBtn" ng-click="addTag()">Add Tag</button>
                                        </form>

                                            <%--Where We add the tag chips--%>
                                        <div id="tagPane" class="noFloat">
                                                <%--The X button should remove the tag instead of hiding it--%>
                                            <div class="chip" ng-repeat="courseTagged in courseTaggedList">
                                                <span class="closebtn" onclick="this.parentElement.style.display='none'">&times;</span>
                                                {{courseTagged}}
                                            </div>

                                        </div>
                                    </div>
                                </div>
                                    <%--Submit button to add the course--%>
                                    <%--<div class="form-group" class="noFloat">--%>
                                <input class="btn btn-primary" ng-click="updateCourse()" name="submit" id="courseSubmit" value="Save Course" type="submit"/>
                                    <%--</div>--%>
                            </div>
                        </div>
                    </c:when>
                    <c:when test="${userType eq 'stud'}">
                    </c:when>
                </c:choose>
            </div>
            <%--SEARCH BAR--%>
            <c:choose>
                <c:when test="${userType eq 'prof'}">
                    <form class="form-inline global-search" role="form">
                        <div class="form-group">
                            <input type="search" class="form-control" id="search" name="search" placeholder="Enter search terms">
                        </div>
                        <button type="submit" id="search_submit" class="btn btn-default">
                            <span class="glyphicon glyphicon-search"></span>&nbspSearch
                        </button>
                    </form>
                </c:when>
            </c:choose>
        </div>
    </div>
    <%--TABS PANE--%>
    <div class="col-md-7 tabPane">
        <div class="panel panel-default">
            <ul class="nav nav-tabs tab-heading" ng-controller="tabsCtrl">
                <li class="tabs clickable" ng-class="tabClass(tab)" ng-repeat="tab in tabs" tab="tab"><a ui-sref="{{tab.state}}" ng-click="setSelectedTab(tab)" ng-bind="tab.label"></a></li>
            </ul>
            <div ui-view></div>
            <div class="panel-body">

            </div>
        </div>
    </div>
    <%--FORUM PANE--%>
    <div class="col-md-3 forumPane" ng-controller="forumCtrl">
        <div class="panel panel-default">
            <div class="panel-group" id="accordion">
                <div class="panel">
                    <input class="panel-heading write-post" ng-model="newPost.header" data-toggle="collapse" data-parent="#accordion" href="#new-post" placeholder="Write a post"></input>
                    <div id="new-post" class="panel-collapse collapse">
                        <input type="text" autocomplate="off" ng-model="newPost.content" class="panel-body form-ctrl course-box" placeholder="Write the body here">
                        <button ng-click="updatePost(newPost)" class="panel-footer form-ctrl btn" ng-disabled="newPost.header==''||newPost.content==''">Create Post</button>
                    </div>
                </div>
                <div ng-repeat="post in posts" class="panel post-header panel-primary">
                    <div class="panel-heading panel-primary">
                        <h4 class="panel-title">
                            <a ng-click="getPosts(post, 0)" data-toggle="collapse" ng-bind="post.header" data-parent="#accordion" href="{{'#collapse' + $index}}"></a>
                        </h4>
                    </div>
                    <div id="{{ 'collapse' + $index }}" class="panel-collapse collapse">
                        <ul class="list-group">
                            <li class="list-group-item list-group-item-info" ng-bind="post.content"></li>
                            <li ng-repeat="comment in post.comments" ng-bind="comment.content" class="list-group-item"></li>
                            <input ng-keyup="$event.keyCode == 13 && updateComment(post, newComment)" ng-model="newComment.content" type="text" autocomplate="off" class="form-ctrl course-box list-group-item"  placeholder="Write new comment">
                        </ul>
                    </div>
                </div>
            </div>

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

    <%--Tab Slide out--%>
    <script src="https://use.fontawesome.com/2be9406092.js"></script>
    <script src="https://rawgit.com/hawk-ip/jquery.tabSlideOut.js/master/jquery.tabSlideOut.js"></script>

    <!-- Include the Quill library -->
    <script src="https://cdn.quilljs.com/1.2.3/quill.js"></script>
    <c:choose>
        <c:when test="${userType eq 'prof'}">
            <script src="<c:url value="/resources/app/js/professor/home.js" />"  type="text/javascript" ></script>
            <script src="<c:url value="/resources/app/js/shared/directives.js" />"  type="text/javascript" ></script>
            <script src="<c:url value="/resources/app/js/professor/course.js" />"  type="text/javascript" ></script>
            <script src="<c:url value="/resources/app/js/professor/announcement.js" />"  type="text/javascript" ></script>
            <script src="<c:url value="/resources/app/js/professor/syllabus.js" />"  type="text/javascript" ></script>
            <script src="<c:url value="/resources/app/js/professor/documents.js" />"  type="text/javascript" ></script>
            <script src="<c:url value="/resources/app/js/forum.js" />"  type="text/javascript" ></script>
        </c:when>
        <c:when test="${userType eq 'stud'}">
            <script src="<c:url value="/resources/app/js/student/home.js" />"  type="text/javascript" ></script>
            <script src="<c:url value="/resources/app/js/shared/directives.js" />"  type="text/javascript" ></script>
            <script src="<c:url value="/resources/app/js/student/course.js" />"  type="text/javascript" ></script>
            <script src="<c:url value="/resources/app/js/student/announcement.js" />"  type="text/javascript" ></script>
            <script src="<c:url value="/resources/app/js/student/documents.js" />"  type="text/javascript" ></script>
            <script src="<c:url value="/resources/app/js/student/syllabus.js" />"  type="text/javascript" ></script>
            <script src="<c:url value="/resources/app/js/forum.js" />"  type="text/javascript" ></script>
        </c:when>
    </c:choose>
    <script src="<c:url value="/resources/app/js/tabRoute.js" />"  type="text/javascript" ></script>
</section>
</html>