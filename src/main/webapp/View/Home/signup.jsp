
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

    <!-- jQuery -->
    <script type="text/javascript" src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>

    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="https://formden.com/static/cdn/bootstrap-iso.css" />
    <!-- Bootstrap Date-Picker Plugin -->
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css"/>

    <!-- Google Font -->
    <link href='//fonts.googleapis.com/css?family=Anton' rel='stylesheet'>

</head>
<body>
<div class="container-fluid introduction">
    <div class="jumbotron">
        <p class="header">Welcome to RoberTak-CS</p><br>
        <p class="subheader">Sign Up</p>
        <form:form method="GET" modelAttribute="person" action="/signup" class="form-horizontal no-margin" id="form">

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
            <label class="control-label col-sm-4" for="dob"> Birthday Date:</label>
            <div class="col-xs-5">
                <form:input path="dob" class="form-control form-control-lg" id="dob" value="" name="dob" placeholder="MM/DD/YYYY" type="text"/>
            </div>
        </div>

        <form path="userType">Are you a	&nbsp
            <input type="radio" name="options" checked id="Professor" value="Professor"> Professor
            <input type="radio" name="options" id="Student" value="Student"> Student
            &nbsp?
        </form>

        <br><br>

        <div>
            <button class="btn btn-primary" name="submit" type="submit">Submit</button>
        </div>

        </form:form>
    </div>
</div>
<link rel="stylesheet" href="<c:url value="/resources/app/css/signup.css" />">

</body>
</html>