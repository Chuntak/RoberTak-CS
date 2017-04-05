
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

    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://formden.com/static/cdn/bootstrap-iso.css" />
    <!-- Google Font -->
    <link href='//fonts.googleapis.com/css?family=Anton' rel='stylesheet'>

    <link rel="stylesheet" href="<c:url value="/resources/app/css/signup.css" />">
    <!-- Red Robbins -->
    <link rel="icon" type="image/png" href="<c:url value="/images/logo.png"/>">
</head>
<body>


<div class="container-fluid introduction">
    <div class="jumbotron">
        <p class="header">Welcome to RoberTak-CS</p><br>

        <form:form method="GET" modelAttribute="person" action="/register" class="form-horizontal no-margin" id="form">

        <div class="form-group">
            <label class="control-label col-sm-4" for="firstName">First Name:</label>
            <div class="col-xs-5">
                <form:input path="firstName" class="form-control form-control-lg" type="text" id="firstName" placeholder="First Name"/>
            </div>
        </div>

        <div class="form-group">
            <Label class="control-label col-sm-4" for="lastName">Last Name:</Label>
            <div class="col-xs-5">
                <form:input path="lastName" class="form-control form-control-lg" type="text" id="lastName" placeholder="Last Name"/>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-sm-4" for="school">School:</label>
            <div class="col-xs-5">
                <form:input path="school" class="form-control form-control-lg" type="text" id="school" placeholder="School Name"/>
            </div>
        </div>

        <div class="form-group">
            <form:input path="email" type="hidden" id="email" value='<%= session.getAttribute("email")%>'/>
        </div>

        <form:radiobutton path="userType" name="options" checked="true" id="Professor" value="prof"/>Professor
        <form:radiobutton path="userType" name="options" id="Student" value="stud"/>Student

        <br><br>
        <div>
            <input class="btn btn-primary" name="submit" value="Sign Up" type="submit"></input>
        </div>
        </form:form>
    </div>
</div>
</body>

<section>
    <!-- jQuery -->
    <script type="text/javascript" src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</section>
</html>