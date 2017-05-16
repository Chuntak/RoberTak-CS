
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
        <%--<link href="https://cdnjs.cloudflare.com/ajax/libs/normalize/7.0.0/normalize.min.css" rel="stylesheet">--%>
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
    <img class="inline_header" src="images/logo-black-toolbar.png"/>
    <h1 class="text">&nbspbackpack</h1>
    <div class="text-logout">Welcome <c:out value="${firstName}"/> &nbsp
        <%--Sign Out button--%>
        <img src="images/logout.png" ng-controller="homeCtrl" ng-click="signOut()" class="logout clickable" data-toggle="tooltip" title="Sign out" data-placement="bottom"/>
        <%--<span ng-controller="homeCtrl" class="glyphicon glyphicon-log-out" ng-click="signOut()" class="logout clickable" data-toggle="tooltip" title="Sign out" data-placement="bottom">Logout</span>--%>

        <form:form id="signOut" name="signOut"  method="GET" action="/signOut">
            <button hidden="hidden" type="submit"></button>
        </form:form>
    </div>
</div>

<div class="content container-fluid app-wrapper row">
    <%--COURSE PANE--%>

    <div class="coursePane col-md-2" ng-controller="courseCtrl">

        <div class="col-md-12 panel panel-default">
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
                            <input type="text" autocomplete="off" class="form-ctrl course-box" id="enroll" ng-model="course.code" name="enroll" placeholder="Course Code">
                            <span class="input-group-btn">
                                    <button class="btn btn-success glyphicon glyphicon-plus course-btn" ng-click="enrollCourse()" type="button"></button>
                                </span>
                        </div>
                    </div>
                </c:when>
            </c:choose>
                <div class="courseSelection-wrapper">
            <%--Course Selection (+Modal)--%>
            <div id="courseSelection" class="scrollable">
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
                                <span class="close" id="courseModelCloseBtn">&times;</span>
                                <h3 class="modalLabel">Courses</h3>

                                <form name="addCourse" ng-submit="updateCourse()">
                                    <%--The course prefix and number box--%>
                                <div class="form-inline form-group">
                                    <div class="col-lg-6">
                                        <input required type="text" class="form-control wide coursePrefix" ng-model="course.prefix" maxlength="8" autocomplete="off" placeholder="Course Prefix" />
                                    </div>
                                    <div class="col-lg-6">
                                        <input required type="text" class="form-control wide courseNumber" ng-model="course.number" maxlength="3" autocomplete="off" placeholder="Course Number">
                                    </div>
                                </div>

                                    <%--The course name--%>
                                <div class="col-lg-12">
                                    <div class="form-group">
                                        <input required type="text" class="form-control text-left courseName" ng-model="course.name" maxlength="32" autocomplete="off" placeholder="Course Name"/>
                                    </div>
                                </div>

                                    <%--Semester and Public checkbox--%>
                                <div class="form-inline">
                                    <div class="col-lg-3">
                                        <select name="semester_chooser" class="form-control wide" ng-model="course.semester">
                                            <option value="" disabled selected>Semester</option>
                                            <option value="Spring">Spring</option>
                                            <option value="Summer">Summer</option>
                                            <option value="Fall">Fall</option>
                                            <option value="Winter">Winter</option>
                                        </select>
                                    </div>
                                    <div class="col-lg-3">
                                        <select name="year_chooser" class="form-control" ng-model="course.ano">
                                            <option value="" disabled selected>Year</option>
                                            <option value="2017">2017</option>
                                            <option value="2018">2018</option>
                                            <option value="2019">2019</option>
                                            <option value="2020">2020</option>
                                        </select>
                                    </div>

                                    <div class="col-lg-6">
                                        <div class="form-check pubDiv">
                                            <label class="form-check-label">Make Course Public</label>
                                            <input class="form-check-input public" ng-model="course.pub" type="checkbox"/>
                                        </div>
                                    </div>
                                </div>
                                    <%--Tag Selection and Removal section--%>
                                <div>
                                    <div class="col-lg-12">
                                        <div class="form-inline">
                                            <input list="tags"  ng-model="selectedTag" class="form-control tagList" name="tags">
                                            <datalist id="tags">
                                                <option ng-repeat="tag in tagList">{{tag}}</option>
                                            </datalist>
                                            <button type="button" class="btn addTagBtn" ng-click="addTag()">Add Tag</button>
                                        </div>

                                            <%--Where We add the tag chips--%>
                                        <div class="tagPane">
                                                <%--The X button should remove the tag instead of hiding it--%>
                                            <div class="chip" ng-repeat="courseTagged in courseTaggedList">
                                                <span class="closebtn" ng-click="deleteTag(courseTagged)">&times;</span>
                                                {{courseTagged}}
                                            </div>

                                        </div>
                                    </div>
                                </div>
                                        <%--SUBMIT BUTTON TO ADD THE COURSE--%>
                                        <div class="text-center">
                                        <input class="btn btn-primary" type="submit" value="Save Course">
                                    </div>
                                </form>
                            </div>
                        </div>
                    </c:when>
                    <c:when test="${userType eq 'stud'}">
                    </c:when>
                </c:choose>
            </div>
                </div>
            <%--SEARCH BAR--%>
            <c:choose>
                <c:when test="${userType eq 'prof'}">
                    <div class="col-md-12">
                        <div class="input-group">
                            <input class="form-control course-box" required type="text" placeholder="Search courses" autocomplete="off"  id="searchInput" ng-model="search.name">
                            <span class="input-group-btn">
                                <a href="#" ng-click="showSearchModel()" type="submit" class="btn btn-default">
                                    <span class="glyphicon glyphicon-menu-down"></span>
                                </a>
                            </span>
                            <span class="input-group-btn">
                                 <button ng-click="searchCourse()" type="submit" id="searchSubmit" class="btn btn-default">
                                   <span class="glyphicon glyphicon-search"></span>
                                 </button>
                            </span>
                        </div>
                        <!-- ADVANCED SEARCH MODAL -->
                        <div id="searchModal" class="modal container-fluid">
                            <!-- Modal content -->
                            <div class="modal-content">
                                <span class="close" id="searchModalCloseBtn">&times;</span>
                                <h3 class="modalLabel">Advanced Search</h3>

                                <form ng-submit="searchCourse()">
                                        <%--The course professor and course name--%>
                                    <div class="form-inline form-group">
                                        <div class="col-lg-6">
                                            <input type="text" class="form-control wide" ng-model="search.profName" maxlength="100" autocomplete="off" placeholder="Course Professor" />
                                        </div>
                                        <div class="col-lg-6">
                                            <input type="text" class="form-control wide" ng-model="search.name" maxlength="32" autocomplete="off" placeholder="Course Name"/>
                                        </div>
                                    </div>
                                        <%--The course prefix and number box--%>
                                    <div class="form-inline form-group">
                                        <div class="col-lg-6">
                                            <input type="text" class="form-control wide" ng-model="search.prefix" maxlength="8" autocomplete="off" placeholder="Course Prefix" />
                                        </div>
                                        <div class="col-lg-6">
                                            <input type="text" class="form-control wide" ng-model="search.number" maxlength="4" autocomplete="off" placeholder="Course Number"/>
                                        </div>
                                    </div>

                                        <%--The course school--%>
                                    <div class="col-lg-12">
                                        <div class="form-group">
                                            <input type="text" class="form-control text-left courseName" ng-model="search.school" maxlength="32" autocomplete="off" placeholder="Course School"/>
                                        </div>
                                    </div>
                                        <%--Semester and Public checkbox--%>
                                    <div class="form-inline">
                                        <div class="col-lg-6">
                                            <select name="semester_chooser" class="form-control wide" ng-model="search.semester">
                                                <option value="" disabled selected>Semester</option>
                                                <option value="">None</option>
                                                <option value="Spring">Spring</option>
                                                <option value="Summer">Summer</option>
                                                <option value="Fall">Fall</option>
                                                <option value="Winter">Winter</option>
                                            </select>
                                        </div>
                                        <div class="col-lg-6">
                                            <select name="year_chooser" class="form-control" ng-model="search.ano">
                                                <option value="" disabled selected>Year</option>
                                                <option value="">None</option>
                                                <option value="2017">2017</option>
                                                <option value="2018">2018</option>
                                                <option value="2019">2019</option>
                                                <option value="2020">2020</option>
                                            </select>
                                        </div>
                                    </div>
                                        <%--Tag Selection and Removal section--%>
                                    <div>
                                        <div class="col-lg-12">
                                            <div class="form-inline">
                                                <input list="searchTags"  ng-model="selectedSearchTag" class="form-control tagList" name="tags">
                                                <datalist id="searchTags">
                                                    <option ng-repeat="tag in tagList">{{tag}}</option>
                                                </datalist>
                                                <button type="button" class="btn addTagBtn" ng-click="addSearchTag()">Add Tag</button>
                                            </div>

                                                <%--Where We add the tag chips--%>
                                            <div class="tagPane">
                                                    <%--The X button should remove the tag instead of hiding it--%>
                                                <div class="chip" ng-repeat="searchTagged in search.tagNames">
                                                    <span class="closebtn" ng-click="deleteSearchTag(searchTagged)">&times;</span>
                                                    {{searchTagged}}
                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                        <%--SUBMIT BUTTON TO SEARCH--%>
                                    <div class="text-center">
                                        <input class="btn btn-primary" type="submit" value="Search Course">
                                    </div>
                                </form>
                            </div>
                        </div>

                        <%--AngularJS to dynamically load the courses--%>
                        <div class="list-grouper" ng-repeat="result in searchCourseResults">
                            <a class="list-group-item"  ng-click="selectCourseResults(result, $index)" ng-class="{active: $index == selectedCourseResult}">
                                <h4 class="card-title row">
                                    <p class="col-sm-9" ng-bind="(result.prefix)+-+(result.number)"></p>
                                </h4>
                                <h6 class="card-subtitle mb-2" ng-bind="result.name"></h6>
                                <h6 class="card-subtitle mb-2" ng-bind="(result.semester)+' '+(result.ano)"></h6>
                                <h6 class="card-subtitle mb-2" ng-bind="(result.profFirstName)+' '+(result.profLastName)"></h6>
                                <h6 class="card-subtitle mb-2" ng-bind="result.profEmail"></h6>
                            </a>
                        </div>

                        <%--PAGINATION--%>
                        <div class="input-group">
                            <span class="input-group-btn">
                                <button class="glyphicon glyphicon-chevron-left btn btn-secondary" ng-click="changePageByOne(-1)" type="button"></button>
                            </span>
                            <label class="form-control" id="paginationLabelId"></label>
                            <span class="input-group-btn">
                                <button class="glyphicon glyphicon-chevron-right btn btn-secondary" ng-click="changePageByOne(1)" type="button"></button>
                            </span>
                        </div>
                    </div>
                </c:when>
            </c:choose>
       </div>
    </div>
    <div class="col-md-10">
        <%--TABS PANE--%>
        <div class="col-md-8 tabPane">
            <div class="tabScroll panel panel-default">
                <ul class="nav nav-tabs tab-heading" ng-controller="tabsCtrl">
                    <li class="tabs clickable" ng-class="tabClass(tab)" ng-repeat="tab in tabs" tab="tab"><a ui-sref="{{tab.state}}" ng-click="setSelectedTab(tab)" ng-bind="tab.label"></a></li>
                </ul>
                <div class="scrollable" ui-view></div>

            </div>
        </div>
        <%--FORUM PANE--%>
        <div class="col-md-4 forumPane">
            <div class="tab-panel panel panel-default">
                <div class="scrollable">
                <fieldset ng-cloak class="panel-group" id="accordion" ng-controller="forumCtrl">
                    <form ng-submit="updatePost(newPost)" class="panel">
                        <span>
                            <input required id="postHeader" class="form-control panel-heading write-post" ng-model="newPost.header" data-toggle="collapse" data-parent="#accordion" href="#new-post" placeholder="Write a post"></input>
                        </span>
                        <div id="new-post" class="panel-collapse collapse">
                            <textarea required id="postContent" type="text" autocomplete="off" ng-model="newPost.content" class="form-control panel-body form-ctrl course-box" placeholder="Write the body here"></textarea>

                            <input ng-model="newPost.anon" type="checkbox" class="custom-control-input">Post Anonymously</input>
                            <button type="submit" class="panel-footer form-ctrl btn btn-default" ng-disabled="courseId==0">Create Post</button>
                        </div>
                    </form>
                    <p ng-if="!posts.length">There are no posts.</p>

                    <div ng-repeat="(pkey,post) in posts" class="posts panel post-header panel-primary">
                        <div class="panel-heading panel-primary post-title">
                            <h4 class="panel-title row"/>
                                <a ng-if="!post.editing" class="post-header col-md-11" ng-click="getPosts(post, 0)" data-toggle="collapse" ng-bind="post.header" data-parent="#accordion" href="{{'#collapse' + $index}}"></a>
                                <input id="{{ 'p-header-' + $index }}" ng-if="editMode && post.editing" class="form-control panel-heading write-post" ng-value="post.header"></input>
                                <c:choose>
                                    <%-- IF OWNER PROFESSOR OR ENROLLED STUDENT, APPLY THIS FUNCTIONALITY --%>
                                    <c:when test="${(userType eq 'prof' && isOwner eq true) || userType eq 'stud'}">
                                        <div class="col-md-1 edit-btns">
                                            <%-- EDIT POST BUTTON --%>
                                            <btn ng-if="post.editable && !editMode && !post.editing" class="btn-xs glyphicon glyphicon-pencil clickable on-show" ng-click="editPost(post)" title="Edit Post"></btn>
                                            <btn ng-if="post.editable && editMode && post.editing" class="btn-xs glyphicon glyphicon-ok-sign clickable" ng-click="saveEdit(post, $index)" title="Save Edit"></btn>
                                            <%-- DELETE POST BUTTON - PROFESSORS CAN ALWAYS DELETE --%>
                                            <c:choose>
                                                <c:when test="${userType eq 'prof' && isOwner eq true}">
                                                    <btn ng-if="!editMode && !post.editing" class="btn-xs glyphicon glyphicon-trash clickable on-show" ng-click="deletePost(post, $index)" title="Delete Post"></btn>
                                                </c:when>
                                                <c:when test="${userType eq 'stud'}">
                                                    <btn ng-if="userId == post.authorId && !editMode && !post.editing" class="btn-xs glyphicon glyphicon-trash clickable on-show" ng-click="deletePost(post, $index)" title="Delete Post"></btn>
                                                </c:when>
                                            </c:choose>
                                            <btn ng-if="editMode && post.editing" class="btn-xs glyphicon glyphicon-remove-sign clickable" ng-click="cancelEdit(post)" title="Cancel Edit"></btn>

                                        </div>
                                    </c:when>
                                </c:choose>
                                <small ng-if="!post.editing">
                                    <span class="col-md-9 dateDisplay" ng-bind="post.dateDisplay"></span>
                                    <span class="col-md-3">
                                        <span class="likes-sect col-md-6">
                                            <span ng-class="post.liked > 0 ? 'liked' : 'not-liked'" ng-click="updateLike(post)" class="likes-icon col-md-6 btn-small btn glyphicon glyphicon-thumbs-up" title="Like Post"></span>
                                            <span class="likes col-md-6" ng-bind="post.likes"></span>
                                        </span>
                                        <span class="cmt-cnt-sect col-md-6">
                                            <span class="cmt-cnt-icon col-md-6 glyphicon glyphicon-comment"></span>
                                            <span class="cmt-cnt col-md-6" ng-bind="post.commentCount"></span>
                                        </span>
                                    </span>
                                </small>
                            </h4>
                        </div>
                        <div  id="{{ 'collapse' + $index }}" class="panel-collapse collapse" ng-class="post.editing ? 'in':''">
                            <ul class="list-group">
                                <li class="list-group-item list-group-item-info post row">
                                    <p ng-if="!post.editing" ng-bind="post.content"></p>
                                    <textarea id="{{ 'p-content-' + $index }}" ng-if="editMode && post.editing" type="text" autocomplete="off" class="form-control panel-body form-ctrl course-box">{{post.content}}</textarea>

                                    <label ng-bind="post.anon ? 'posted by ' + 'anonymous' : 'posted by ' + post.firstName + ' ' + post.lastName" ></label>
                                </li>
                                <li ng-repeat="comment in post.comments"  class="list-group-item post row">
                                    <div class="col-md-1 col-md-offset-10 edit-btns">
                                            <%-- EDIT POST BUTTON --%>
                                        <btn ng-if="comment.editable && comment.editing" class="btn-xs glyphicon glyphicon-ok-sign clickable" ng-click="saveEdit(post, pkey, comment, $index)" title="Save Edit"></btn>
                                            <%-- DELETE POST BUTTON - PROFESSORS CAN ALWAYS DELETE --%>
                                        <c:choose>
                                            <c:when test="${userType eq 'prof' && isOwner eq true}">
                                                <div ng-if="!comment.editing" class="dropdown">
                                                    <div ng-if="!comment.editing" class="glyphicon glyphicon-menu-down dropdown-toggle clickable edit-dropdown" type="button" data-toggle="dropdown"></div>
                                                    <ul class="dropdown-menu">
                                                        <li><a ng-if="comment.editable && !comment.editing" ng-click="editPost(comment)" class="edit-options">Edit</a></li>
                                                        <c:choose>
                                                            <c:when test="${userType eq 'prof' && isOwner eq true}">
                                                                <li><a ng-if="!comment.editing" ng-click="deletePost(post, pkey, comment, $index)" class="edit-options">Delete</a></li>
                                                            </c:when>
                                                            <c:when test="${userType eq 'stud'}">
                                                                <li><a ng-if="userId == comment.authorId && !comment.editing" ng-click="deletePost(post, pkey, comment, $index)" class="edit-options">Delete</a></li>
                                                            </c:when>
                                                        </c:choose>
                                                    </ul>
                                                </div>
                                            </c:when>
                                        </c:choose>
                                        <btn ng-if="comment.editing" class="btn-xs glyphicon glyphicon-remove-sign clickable" ng-click="cancelEdit(comment)" title="Cancel Edit"></btn>

                                    </div>
                                    <p ng-if="!comment.editing" class="col-md-12" ng-bind="comment.content"></p>
                                    <textarea ng-if="comment.editing" id="{{ 'c-content-' + pkey + $index }}" type="text"  type="text" autocomplete="off" class="form-control form-ctrl course-box list-group-item">{{ comment.content }}</textarea>

                                    <label class="col-md-9 author" ng-bind="comment.anon ? 'posted by ' + 'anonymous' : 'posted by ' + comment.firstName + ' ' + comment.lastName" ></label>
                                    <label class="col-md-3" ng-bind="comment.dateCreated" ></label>
                                </li>
                                <form ng-submit="updateComment(post, newComment)">
                                    <textarea required type="text" ng-keyup="newComment.content!=='' && $event.keyCode == 13 && updateComment(post, newComment)" ng-model="newComment.content" type="text" autocomplete="off" class="form-control form-ctrl course-box list-group-item"  placeholder="Write new comment"></textarea>
                                </form>
                            </ul>
                        </div>
                    </div>

                </fieldset>
            </div>

            </div>
        </div>
    </div>
    <%--<div class="push"></div>--%>
</div>
<!--App Content END-->
<%--FOOTER--%>
<%--<div class="footer footer-content">--%>
    <%--<img src="images/logo_footer.png"> &nbsp--%>
    <%--<span class="text-muted">Copyright &copy 2017 backpack Red Robins. All Rights Reserved.</span>--%>
<%--</div>--%>

</body>

<!-- JavaScripts -->
<section>
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script>
    <script src="https://apis.google.com/js/api:client.js"></script>
    <%--Google Logout End--%>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <%-- TIMEAGO - USED TO DISPLAY TIMES ON POSTS, COMMENTS, ETC IN FORMAT '2hrs ago' --%>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-timeago/1.5.4/jquery.timeago.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="<c:url value="/resources/app/js/imported/bootstrap-datepicker.min.js" />"  type="text/javascript" ></script>
    <script src="<c:url value="/resources/app/js/imported/bootstrap-timepicker.js" />"  type="text/javascript" ></script>
    <!-- App Base -->
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-router/0.4.2/angular-ui-router.min.js"></script>
    <script src="https://kendo.cdn.telerik.com/2017.1.118/js/kendo.all.min.js"></script>
    <script src="https://angular-ui.github.io/bootstrap/ui-bootstrap-tpls-2.5.0.js"></script>


    <script src="<c:url value="/resources/app/js/imported/Chart.Core.min.js" />"  type="text/javascript" ></script>
    <script src="<c:url value="/resources/app/js/imported/Chart.Scatter.min.js" />"  type="text/javascript" ></script>


    <!-- Include the Quill library -->
    <script src="https://cdn.quilljs.com/1.2.3/quill.js"></script>
    <c:choose>
        <c:when test="${userType eq 'prof'}">
            <script src="<c:url value="/resources/app/js/professor/home.js" />"  type="text/javascript" ></script>
            <script src="<c:url value="/resources/app/js/shared/directives.js" />"  type="text/javascript" ></script>
            <script src="<c:url value="/resources/app/js/professor/course.js" />"  type="text/javascript" ></script>
            <script src="<c:url value="/resources/app/js/professor/announcement.js" />"  type="text/javascript" ></script>
            <script src="<c:url value="/resources/app/js/professor/assignment.js" />"  type="text/javascript" ></script>
            <script src="<c:url value="/resources/app/js/professor/quiz.js" />"  type="text/javascript" ></script>
            <script src="<c:url value="/resources/app/js/professor/syllabus.js" />"  type="text/javascript" ></script>
            <script src="<c:url value="/resources/app/js/professor/documents.js" />"  type="text/javascript" ></script>
            <script src="<c:url value="/resources/app/js/professor/grades.js" />"  type="text/javascript" ></script>
            <script src="<c:url value="/resources/app/js/forum.js" />"  type="text/javascript" ></script>
        </c:when>
        <c:when test="${userType eq 'stud'}">
            <script src="<c:url value="/resources/app/js/student/home.js" />"  type="text/javascript" ></script>
            <script src="<c:url value="/resources/app/js/shared/directives.js" />"  type="text/javascript" ></script>
            <script src="<c:url value="/resources/app/js/student/course.js" />"  type="text/javascript" ></script>
            <script src="<c:url value="/resources/app/js/student/announcement.js" />"  type="text/javascript" ></script>
            <script src="<c:url value="/resources/app/js/student/assignment.js" />"  type="text/javascript" ></script>
            <script src="<c:url value="/resources/app/js/student/quiz.js" />"  type="text/javascript" ></script>
            <script src="<c:url value="/resources/app/js/student/documents.js" />"  type="text/javascript" ></script>
            <script src="<c:url value="/resources/app/js/student/syllabus.js" />"  type="text/javascript" ></script>
            <script src="<c:url value="/resources/app/js/student/grades.js" />"  type="text/javascript" ></script>
            <script src="<c:url value="/resources/app/js/forum.js" />"  type="text/javascript" ></script>
        </c:when>
    </c:choose>
    <script src="<c:url value="/resources/app/js/tabRoute.js" />"  type="text/javascript" ></script>
</section>
</html>