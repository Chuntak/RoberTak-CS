
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
        <script src="<c:url value="/resources/app/js/ang_test.js" />"  type="text/javascript" ></script>

        <!--KENDO UI -->
        <!-- THE FOLLOWING SCRIPT (KENDO) REQUIRES JQUERY & ANGULARJS-->
        <!--<script src="https://kendo.cdn.telerik.com/2017.1.118/js/jquery.min.js"></script>-->
        <!--<script src="https://kendo.cdn.telerik.com/2017.1.118/js/angular.min.js"></script>-->
        <script src="https://kendo.cdn.telerik.com/2017.1.118/js/kendo.all.min.js"></script>
        <!--KENDO UI-->
    </section>


    <title>TODO LIST MAKER</title>

</head>
<body ng-app="myApp" ng-controller="myCtrl">

    <!--App Content START-->
    <h1><span class="label label-default">Welcome {{userName}}</span></h1>
    <div class="app-wrapper">
        <div id="toolbar">
            <span>
            <button ng-click="newList()"  type="button" class="btn btn-success" data-toggle="tooltip" title="Create" data-placement="bottom">
                <span class="glyphicon glyphicon-plus-sign"></span>
            </button>
            <button ng-click="loadListBTN()" type="button" class="btn btn-default" data-toggle="tooltip" title="Load" data-placement="bottom">
                <span class="glyphicon glyphicon-folder-open"></span>
            </button>

            <button ng-click="save()" ng-disabled="notsaveable" type="button" class="btn btn-primary" data-toggle="tooltip" title="Save" data-placement="bottom">
                <span class="glyphicon glyphicon-floppy-disk"></span>
            </button>

            <form:form id="loadPage_index" name="loadPage_index" method="GET" action="/loadPage_index">
                <button class="btn-hide" type="submit"></button>
            </form:form>

            <form:form method="GET" action="/logout">
                <a href="https://accounts.google.com/logout">
                    <button onclick="signOut()" type="submit" class="btn btn-default" data-toggle="tooltip" title="Sign out" data-placement="bottom">
                            <%--<a href="https://accounts.google.com/logout" onclick="signOut();">--%>
                        <span class="glyphicon glyphicon-log-out"></span>
                    </button>
                </a>
            </form:form>

            <form:form id="dbAccess" name="dbAccess" method="GET" action="/dbAccessPage">
                <button type="submit">Access DB</button>
            </form:form>

            </span>

        </div>

        <div id="mid" ng-show="show">
            <h1>To Do List</h1>
        </div>

        <label>
            {{printDB}}
        </label>

        <select ng-disabled="newListMode" ng-change="onListSelected()" ng-model="selectedList" ng-options="toDoList.listName for toDoList in toDoListList"></select>
        <label>
            <input type="radio" ng-disabled="newListMode" ng-click="loadLists()" ng-model="viewMyList" value=true>
            View My Lists
        </label>
        <label>
            <input type="radio" ng-disabled="newListMode" ng-click="loadLists()" ng-model="viewMyList" value=false>
            View Other Lists
        </label>


        <div id="details" ng-show="show">
            <form name="listForm">
                <h2>Details</h2>
                <br/>
                <div class="form-group">

                        <label for="nameOfList">Name of List:</label>
                        <input type="text" class="form-control" id="nameOfList" ng-model="nameOfList">

                </div>
                <div class="form-group">
                    <label for="author">Author:</label>
                    <input type="text" readonly="true" class="form-control" id="author" ng-model="ownerOfList">
                    <label>Private:
                        <input type="checkbox" ng-model="private"
                               ng-true-value="true" ng-false-value="false">
                    </label><br/>
                </div>

            </form>
        </div>

        <div id="items" ng-show="show">
            <h3>Items</h3>

            <kendo-grid id="grid" options="mainGridOptions">

            </kendo-grid>

        </div>
    </div>
    <!--App Content END--

    <!-- CSS START -->
    <section>
        <link rel="stylesheet" href="<c:url value="/resources/app/css/todolist_maker.css" />">

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

        <!--KENDO UI -->
        <link rel="stylesheet" href="https://kendo.cdn.telerik.com/2017.1.118/styles/kendo.common-material.min.css" />
        <link rel="stylesheet" href="https://kendo.cdn.telerik.com/2017.1.118/styles/kendo.material.min.css" />
        <link rel="stylesheet" href="https://kendo.cdn.telerik.com/2017.1.118/styles/kendo.material.mobile.min.css" />

    </section>
    <!-- CSS END -->



</body>
</html>