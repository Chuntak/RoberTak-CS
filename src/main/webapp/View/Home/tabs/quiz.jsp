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
        <h3><input type="text" ng-model="addQuiz.title" ></h3>
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
                <div class="chip" ng-repeat="quizTagged in addQuiz.quizTaggedList">
                    <span class="closebtn" ng-click="removeTag(quizTagged,addQuiz)">&times;</span>
                    {{quizTagged}}
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
                    <input type="text" ng-model="question.question">
                </div>
                <div class="form-group form-inline">
                    <label>Question Answer</label>
                    <input tyoe="text" ng-model="question.answer">
                </div>
            </div>
        </div>
        <div>
            <button class="btn btn-primary" ng-click="makeQuestion(addQuiz)">Add new question</button>
        </div>
        <button class="btn btn-primary" ng-click="saveQuiz(addQuiz)">Save the Quiz</button><button class="btn btn-primary" ng-click="cancelQuizCreation(addQuiz)">Cancel Quiz Creation</button>
    </div>


    <%--The Created Quizzes--%>
    <div class="list-group">
        <div ng-repeat="quiz in quizList" class="list-group-item">
            <div class="quizCard">
                <h4 ng-bind="quiz.title"></h4>
                <h6 ng-bind="'Due ' + quiz.date + ' ' + quiz.time"></h6>
                <p ng-bind="Total"></p>
                <a id="createAsgmt" data-toggle="collapse" data-target="#quizEditor{{$index}}">Edit this Quiz</a>
            </div>
            <div class="collapse" id="quizEditor{{$index}}">
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
                            <option ng-repeat="tag in tagList">{{tag}}</option>
                        </datalist>
                        <button type="button" class="btn addTagBtn" ng-click="addTag(quiz)">Add Tag</button>
                    </div>
                    <%--Where We add the tag chips--%>
                    <div class="tagPane">
                    <%--The X button should remove the tag instead of hiding it--%>
                        <div class="chip" ng-repeat="quizTagged in quiz.quizTaggedList">
                            <span class="closebtn" ng-click="removeTag(quizTagged,quiz)">&times;</span>
                            {{quizTagged}}
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
                            <input type="text" ng-model="question.question">
                        </div>
                        <div class="form-group form-inline">
                            <label>Question Answer</label>
                            <input tyoe="text" ng-model="question.answer">
                        </div>
                    </div>
                </div>
                <div>
                    <button class="btn btn-primary" ng-click="addQuestion(quiz)">Add new question</button>
                </div>
                <button data-toggle="collapse" data-target="#quizEditor{{$index}}" class="btn btn-primary" ng-click="saveQuestion(quiz)">Save Edit</button>
                <button data-toggle="collapse" data-target="#quizEditor{{$index}}" class="btn btn-primary" ng-click="cancel(quiz)">Cancel Edit</button>
            </div>
        </div>
    </div>
</div>

</body>


</html>
