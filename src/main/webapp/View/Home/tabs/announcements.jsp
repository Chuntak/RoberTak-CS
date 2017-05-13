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
<body ng-controller="announcementsCtrl">
<div class="list-group" id="announcementDiv">
    <c:choose>
        <c:when test="${userType eq 'prof' && isOwner eq true}">
            <%--Adding an announcement--%>

            <div class="list-group-item-success">
                <h3 class="panel-title">
                    <a id="createAnnoucnement" data-toggle="collapse" data-target="#collapser">Add New Announcement</a>
                </h3>
            </div>
            <div class="collapse" id="collapser">
            <div class="list-group-item" id="addAnnouncementDiv">
                <form id="addAnnouncementForm">
                    <h4 id="addAnnouncementTitleContainer">
                        <input id="addAnnouncementTitle" type="text" class="col-xs-12" placeholder="Announcement Title" maxlength="30">
                    </h4>
                    <p class="error" id="addAnnouncementTitleEmpty">Announcement Title is required!</p>
                    <p class="error" id="addAnnouncementTitleLength">Announcement Title is too long!</p>
                    <!-- The Adding Editor-->
                    <div id="editor"></div>
                    <p class="error" id="addAnnouncementQuillError">Announcement Description is Empty!</p>
                </form>
                <button type="submit" class="btn btn-primary" ng-click="addAnnouncement()" data-toggle="collapse" data-target="#collapser">Add Announcement</button>
            </div>
            </div>
        </c:when>
    </c:choose>

    <%--AnnoucementContainer contains each announcement--%>
    <div class="list-group-item announcement" id="announcement-{{$index}}" ng-repeat="announcement in announcementList" testdirective="">

            <c:choose>
                <c:when test="${userType eq 'prof' && isOwner eq true}">
                    <%--Do not use ng-model for title so we save the title if they select cancel edit--%>
                    <span ng-click="deleteAnnouncement(announcement)" class="badge btn-xs col-sm-1 glyphicon glyphicon-trash clickable on-show"></span>
                    <span ng-click="editAnnouncement(announcement,$index)" class="badge btn-xs col-sm-1 glyphicon glyphicon-pencil clickable on-show"></span>

                    <h4><input type="text" class="announcementTitle" placeholder="Announcement Title" id="announcementTitle-{{$index}}" value="{{announcement.title}}"  maxlength="30" disabled="disabled">
                    </h4>

                    <span class="error" id="announcementTitleEmpty-{{$index}}">Announcement Title is required!</span>
                    <span class="error" id="announcementTitleLength-{{$index}}">Announcement Title is too long!</span>


                </c:when>
                <c:when test="${userType eq 'stud' || isOwner eq false}">
                    <%--Do not use ng-model for title so we save the title if they select cancel edit--%>
                    <h4 class="announcementTitle" id="announcementTitle-{{$index}}">{{announcement.title}}</h4>
                </c:when>
            </c:choose>

        <h6>{{announcement.dateCreated}}</h6>
        <%--Announcement Description [Div that gets turned into a quill editor]--%>
        <div class="annnouncementEditors" id="announcementDescription-{{$index}}"></div>
        <c:choose>
            <c:when test="${userType eq 'prof' and isOwner eq true}">
                <span class="error" id="announcementQuillError-{{$index}}">Announcement Description is Empty!</span>
                <br>
                <button class="updateAnnouncement btn btn-primary" id="updateButton-{{$index}}" ng-click="updateAnnouncement(announcement, $index)">Update Announcement</button>
                <button class="cancelEdit btn btn-primary" id="cancelEdit-{{$index}}" ng-click="cancelEdit(announcement, $index)">Cancel Edit</button>
            </c:when>
        </c:choose>
    </div>
</div>
</body>
</html>