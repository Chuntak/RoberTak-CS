<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8"  %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">

    <!-- Include stylesheet -->
    <link href="https://cdn.quilljs.com/1.2.3/quill.snow.css" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value="/resources/app/css/announcements.css" />">

    <title>Announcements</title>
</head>
<body ng-controller="announcementsCtrl" id="announcementBody" class="container-fluid">
<div class="container-fluid" id="announcementDiv">
    <c:choose>
        <c:when test="${userType eq 'prof'}">
            <%--Adding an announcement--%>
            <div id="addAnnouncementDiv">
                <form id="addAnnouncementForm">
                    <div class="row">
                        <input id="addAnnouncementTitle"type="text" class="col-xs-12" placeholder="Announcement Title" maxlength="30">
                    </div>
                    <span class="error" id="addAnnouncementTitleEmpty">Announcement Title is required!</span>
                    <span class="error" id="addAnnouncementTitleLength">Announcement Title is too long!</span>
                    <!-- The Adding Editor-->
                    <div id="editor"></div>
                    <span class="error" id="addAnnouncementQuillError">Announcement Description is Empty!</span>
                </form>
                <button class="btn-md btn-primary" ng-click="addAnnouncement()">Add Announcement</button>
            </div>
            <button class="btn-info btn-block" ng-click="toggleAdd()">Show/Hide Announcement Form</button>
        </c:when>
    </c:choose>

    <%--AnnoucementContainer contains each announcement--%>
    <div class="announcementContainer" id="announcement-{{$index}}" ng-repeat="announcement in announcementList" testdirective="">
        <div class="row">
            <c:choose>
                <c:when test="${userType eq 'prof'}">
                    <%--Do not use ng-model for title so we save the title if they select cancel edit--%>
                    <h4><input type="text" class="col-xs-10 announcementTitle" placeholder="Announcement Title" id="announcementTitle-{{$index}}" value="{{announcement.title}}"  maxlength="30" disabled="disabled" >
                    </h4>
                    <btn class="btn-md col-sm-1 glyphicon glyphicon-pencil clickable on-show" ng-click="editAnnouncement(announcement,$index)">
                    </btn>
                    <btn class="btn-md col-sm-1 glyphicon glyphicon-trash clickable on-show" ng-click="deleteAnnouncement(announcement)">
                    </btn>
                </c:when>
                <c:when test="${userType eq 'stud'}">
                    <%--Do not use ng-model for title so we save the title if they select cancel edit--%>
                    <h4 class="announcementTitle" id="announcementTitle-{{$index}}">{{announcement.title}}</h4>
                </c:when>
            </c:choose>
        </div>

        <span class="error" id="announcementTitleEmpty-{{$index}}">Announcement Title is required!</span>
        <span class="error" id="announcementTitleLength-{{$index}}">Announcement Title is too long!</span>

        <div>{{announcement.dateCreated}}</div>
        <%--Announcement Description [Div that gets turned into a quill editor]--%>
        <div class="annnouncementEditors" id="announcementDescription-{{$index}}"></div>
        <c:choose>
            <c:when test="${userType eq 'prof'}">
                <span class="error" id="announcementQuillError-{{$index}}">Announcement Description is Empty!</span>
                <br>
                <button class="updateAnnouncement btn-md btn-primary" id="updateButton-{{$index}}" ng-click="updateAnnouncement(announcement, $index)">Update Announcement</button>
                <button class="cancelEdit btn-md btn-primary" id="cancelEdit-{{$index}}" ng-click="cancelEdit(announcement, $index)">Cancel Edit</button>
            </c:when>
        </c:choose>
    </div>
</div>
</body>
</html>