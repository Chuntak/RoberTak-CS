
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8"  %>

<!DOCTYPE html>
<html>
<head>
    <!-- Red Robbins -->
    <link rel="icon" type="image/png" href="<c:url value="/images/logo.png"/>">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://formden.com/static/cdn/bootstrap-iso.css" />
    <!-- Google Font -->
    <link href='//fonts.googleapis.com/css?family=Anton' rel='stylesheet'>

    <%--CSS--%>
    <link rel="stylesheet" href="<c:url value="/resources/app/css/signup.css" />">

</head>
<body>

<div class="container-fluid introduction">
    <div class="jumbotron">
        <p class="header">Welcome to backpack</p><br>

        <form:form method="GET" modelAttribute="person" action="/register" class="form-horizontal no-margin" id="form">

        <div class="form-group">
            <label class="control-label col-sm-4" for="firstName"></label>
            <form:input path="firstName" class="form-control form-control-lg input-lg" type="text" id="firstName" placeholder="First Name"/>
        </div>

        <div class="form-group">
            <Label class="control-label col-sm-4" for="lastName"></Label>
            <form:input path="lastName" class="form-control form-control-lg input-lg" type="text" id="lastName" placeholder="Last Name"/>
        </div>

        <div class="form-group">
            <label class="control-label col-sm-4" for="school"></label>
            <form:input path="school" class="form-control form-control-lg input-lg" type="text" id="school" placeholder="School Name"/>
        </div>

        <div class="form-group">
            <form:input path="email" type="hidden" id="email" value='<%= session.getAttribute("email")%>'/>
        </div>

        <div class="bottom-form">
            <form:radiobutton path="userType" name="options" checked="true" id="Professor" value="prof"/>&nbspProfessor
            &nbsp&nbsp
            <form:radiobutton path="userType" name="options" id="Student" value="stud"/>&nbspStudent
            <br><br><br>

        <div>
            <input class="btn btn-lg btn-primary" name="submit" value="Sign Up" type="submit"></input>
        </div>

        </form:form>
        </div>
    </div>
</div>

<%--FOOTER--%>
<div id="footer">
    <img src="images/logo_footer.png"> &nbsp
    <span class="text-muted">Copyright &copy 2017 backpack Red Robins. All Rights Reserved.</span>
</div>

</body>
<section>
    <!-- jQuery -->
    <script type="text/javascript" src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</section>
</html>