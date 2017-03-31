<%--
  Created by IntelliJ IDEA.
  User: Chuntak
  Date: 3/29/2017
  Time: 8:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8"  %>
<!DOCTYPE html>
<html>
<head>
    <title>Db Access</title>
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
    <!-- JavaScripts -->
    <section>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

        <!-- Google Code Prettify -->
        <script src="<c:url value="/resources/library-vendor/google-code-prettify/prettify.js" />" type="text/javascript"></script>

        <!-- App Base -->
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
        <script src="<c:url value="/resources/app/js/dbAngular.js" />"  type="text/javascript" ></script>

    </section>
</head>
<body ng-app="myApp" ng-controller="dbCtrl">
    <label>
        Enter SQL Query:
        <input type="text" name="input" ng-model="sqlQuery">
        <button ng-click="requestToDB()"  type="button">Send Request</button>
    </label>

    <table border="1">
        <tr><th ng-repeat="(key, val) in dbTable[0]">{{key}}</th></tr>
        <tr ng-repeat="data in dbTable"><td ng-repeat="(key, val) in data">{{val}}</td></tr>
    </table>
</body>
</html>
