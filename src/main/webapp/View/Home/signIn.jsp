<!--
Created by IntelliJ IDEA.
User: rvtru
Date: 2/14/2017
Time: 1:58 PM
To change this template use File | Settings | File Templates.
-->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>RoberTak-CS</title>
    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="<c:url value="/resources/app/css/login.css" />">
    <!-- Red Robbins -->
    <link rel="icon" type="image/png" href="<c:url value="/images/logo.png" />">
</head>

<body ng-app="signInApp" ng-controller="signInCtrl">
    <div class="row">

        <form:form id="signUp" name="signUp" method="GET" action="/signUp">
            <button hidden="hidden" type="submit"></button>
        </form:form>
        <form:form id="index" name="index" method="GET" action="/index">
            <button hidden="hidden" type="submit"></button>
        </form:form>
        <script type='text/javascript'>
            /* CHECK IF USER IS LOGGED IN ALREADY */
            if(sessionStorage.length > 0) {
                document.forms["index"].submit();
            }
        </script>
        <div id="image-wrapper">
            <img src="images/logo.png"/>
        </div>

            <form:form method="GET" action="/login">
                <div id="login_ID">
                    <div id="gSignInWrapper">
                        <div id="customBtn" class="customGPlusSignIn">
                            <div id="btn-wrapper">
                            <button type="button" class = "btn btn-lg btn-primary">Sign in</button>
                            </div>
                        </div>
                    </div>
                </div>
            </form:form>

        <footer class="footer">
            <div class="container">
                <span class="text-muted">2017 RoberTak-CS Red Robins All Rights Reserved.</span>
            </div>
        </footer>
    </div>

<!-- JavaScripts -->
<section>
    <script src="https://apis.google.com/js/api:client.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
    <script src="<c:url value="/resources/app/js/signIn.js" />"  type="text/javascript" ></script>
</section>


</body>

</html>