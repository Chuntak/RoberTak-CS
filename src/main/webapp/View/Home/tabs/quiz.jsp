<%--
  Created by IntelliJ IDEA.
  User: Calvin
  Date: 5/5/2017
  Time: 3:27 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8"  %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Quiz</title>

    <link rel="stylesheet" href="<c:url value="/resources/app/css/quiz.css" />">
</head>
<body>

<div ng-controller="quizCtrl">
    <button class="btn btn-primary" ng-click="makeQuiz()" id="addQuizBtn">Create a Quiz</button>

    <div id="quizCreation" ng-model="addQuiz">
        <input type="text" class="" value="{{addQuiz.title}}" >
        <%-- DATE PICKER --%>
        <div class="input-group date asgmt-form" id="datepicker" data-provide="datepicker">
            <label for="date" class="col-2 col-form-label">Due Date</label>
            <input ng-model="addQuiz.date" id="date" required type="text" class="form-control">
            <div class="input-group-addon">
                <span class="glyphicon glyphicon-th"></span>
            </div>
        </div>
        <%-- TIME PICKER --%>
        <div class="input-group bootstrap-timepicker timepicker asgmt-form">
            <label for="timepicker1" class="col-2 col-form-label">Due Time</label>
            <input ng-model="addQuiz.time" required id="timepicker1" type="text" class="form-control input-small">
            <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
        </div>
        <%--Tag Selection and Removal section--%>
        <div class="col-lg-12">
            <div class="form-inline">
                <input list="tags"  ng-model="addQuiz.selectedTag" class="form-control tagList" name="tags">
                <datalist id="tags">
                    <option ng-repeat="tag in tagList">{{tag}}</option>
                </datalist>
                <button type="button" class="btn addTagBtn" ng-click="addTag(addQuiz)">Add Tag</button>
            </div>

            <%--Where We add the tag chips--%>
            <div class="tagPane">
                <%--The X button should remove the tag instead of hiding it--%>
                <div class="chip" ng-repeat="courseTagged in addQuiz.courseTaggedList">
                    <span class="closebtn" ng-click="removeTag(courseTagged,addQuiz)">&times;</span>
                    {{courseTagged}}
                </div>

            </div>
        </div>
        <%--Questions are displayed here--%>
        <div class="questionDiv">
            <div ng-repeat="question in addQuiz.questionList">
                <div class="form-group form-inline">
                    <label>Question Title</label>
                    <input type="text" ng-model="question.title">
                </div>
                <div class="form-group form-inline">
                    <label>Question</label>
                    <input type="text" ng-model="question.description">
                </div>
                <div class="form-group form-inline">
                    <label>Question Answer</label>
                    <input tyoe="text" ng-model="question.answer">
                </div>
                <button class="btn btn-primary saveQuestion(addQuestion)">Save Question</button>
            </div>
        </div>
        <div>
            <button class="btn btn-primary" ng-click="addQuestion(addQuiz)">Add new question</button>
        </div>
        <button class="btn btn-primary" ng-click="saveQuiz(addQuiz)">Save the Quiz</button><button class="btn btn-primary" ng-click="cancel(addQuiz)">Cancel Quiz Creation</button>
    </div>

    <div ng-repeat="quiz in quizList">
        <input type="text" class="" value="{{quiz.title}}" >
        <%-- DATE PICKER --%>
        <div class="input-group date asgmt-form" id="datepicker" data-provide="datepicker">
            <label for="dater" class="col-2 col-form-label">Due Date</label>
            <input ng-model="quiz.date" id="dater" required type="text" class="form-control">
            <div class="input-group-addon">
                <span class="glyphicon glyphicon-th"></span>
            </div>
        </div>
        <%-- TIME PICKER --%>
        <div class="input-group bootstrap-timepicker timepicker asgmt-form">
            <label for="timepicker" class="col-2 col-form-label">Due Time</label>
            <input ng-model="quiz.time" required id="timepicker" type="text" class="form-control input-small">
            <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
        </div>
        <%--Tag Selection and Removal section--%>
        <div class="col-lg-12">
            <div class="form-inline">
                <input list="taggs"  ng-model="quiz.selectedTag" class="form-control tagList" name="taggs">
                <datalist id="taggs">
                    <option ng-repeat="tagg in tagList">{{tag}}</option>
                </datalist>
                <button type="button" class="btn addTagBtn" ng-click="addTag(quiz)">Add Tag</button>
            </div>

            <%--Where We add the tag chips--%>
            <div class="tagPane">
                <%--The X button should remove the tag instead of hiding it--%>
                <div class="chip" ng-repeat="courseTagged in quiz.courseTaggedList">
                    <span class="closebtn" ng-click="removeTag(courseTagged,quiz)">&times;</span>
                    {{courseTagged}}
                </div>

            </div>
        </div>
        <%--Questions are displayed here--%>
        <div class="questionDiv">
            <div ng-repeat="question in quiz.questionList">
                <div class="form-group form-inline">
                    <label>Question Title</label>
                    <input type="text" ng-model="question.title">
                </div>
                <div class="form-group form-inline">
                    <label>Question</label>
                    <input type="text" ng-model="question.description">
                </div>
                <div class="form-group form-inline">
                    <label>Question Answer</label>
                    <input tyoe="text" ng-model="question.answer">
                </div>
                <button class="btn btn-primary">Save Question</button>
            </div>
        </div>
        <div>
            <button class="btn btn-primary" ng-click="addQuestion(quiz)">Add new question</button>
        </div>
        <button class="btn btn-primary" ng-click="saveQuestion(quiz)">Save the Quiz</button><button class="btn btn-primary" ng-click="cancel(quiz)">Cancel Quiz Creation</button>
    </div>

</div>


</body>


</html>
