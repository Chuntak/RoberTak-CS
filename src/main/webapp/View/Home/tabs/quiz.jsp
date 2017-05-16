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

    <link rel="stylesheet" href="<c:url value="/resources/app/css/imported/bootstrap-datepicker.css" />">
    <link rel="stylesheet/less" href="<c:url value="/resources/app/css/imported/timepicker.less" />">
    <link rel="stylesheet" href="<c:url value="/resources/app/css/quiz.css" />">
</head>
<body>

<div ng-controller="quizCtrl">
    <c:choose>
        <%--IF PROFESSOR, APPLY THIS FUNCTIONALITY--%>
        <c:when test="${userType eq 'prof' && isOwner eq true}">
            <%--CREATE QUIZ BUTTON--%>
            <button class="btn btn-primary" ng-click="makeQuiz()" id="addQuizBtn">Create a Quiz</button>
            <%--CREATE A QUIZ--%>
            <div id="quizCreation" ng-model="addQuiz">
                <h3><input type="text"  placeholder="Title" ng-model="addQuiz.title" ></h3>
                <h3><input type="text"  placeholder="Max Grade" ng-model="addQuiz.maxGrade" ></h3>
                    <%-- DATE PICKER --%>
                <div class="input-group date asgmt-form datepicker" id="datepicker" data-provide="datepicker">
                    <label for="date" class="col-2 col-form-label">Due Date</label>
                    <input ng-model="addQuiz.date" id="date" required type="text" class="form-control">
                    <div class="input-group-addon">
                        <span class="glyphicon glyphicon-th"></span>
                    </div>
                </div>
                    <%-- TIME PICKER --%>
                <div class="input-group bootstrap-timepicker asgmt-form">
                    <label for="timepicker" class="col-2 col-form-label">Due Time</label>
                    <input ng-model="addQuiz.time" required id="timepicker" type="text" class="form-control input-small timepicker">
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
                            <span class="closebtn" ng-click="deleteTag(quizTagged,addQuiz)">&times;</span>
                            {{quizTagged}}
                        </div>

                    </div>
                </div>
                    <%--Questions are displayed here--%>
                <div class="questionDiv">
                    <div ng-repeat="question in addQuiz.questionList">
                        <form class="form-group form-inline">
                            <label for="shortAnswerMaker{{$index}}">Short Answer</label>
                            <input id="shortAnswerMaker{{$index}}" ng-click="changeQuestionType(question)" type="radio" name="content" ng-model="question.type" value="ShortAns">
                            <label for="multipleChoiceMaker{{$index}}">Multiple Choice</label>
                            <input id="multipleChoiceMaker{{$index}}" ng-click="changeQuestionType(question)" type="radio" name="content" ng-model="question.type" value="M/C">
                            <span ng-click="deleteQuestion(addQuiz,question)" class="btn-xs glyphicon glyphicon-trash clickable"></span>
                        </form>
                        <div class="form-group form-inline">
                            <label>Question</label>
                            <input type="text" ng-model="question.question">
                            <br>
                            <div ng-if="question.type === 'ShortAns'" >
                                <label>Question Answer</label>
                                <input tyoe="text" ng-model="question.answer">
                            </div>
                            <form ng-if="question.type === 'M/C'">
                                <div ng-repeat="choice in question.choices">
                                    <input type="radio" required name="choices" ng-click="setMCAnswer(question,choice)" value="{{question.choices.indexOf(choice)+1|character}}">{{question.choices.indexOf(choice)+1|character}}
                                    <input type="text" id="multipleChoiceQuestion0{{$parent.$index}}{{$index}}" ng-model="choice.answerChoice" class="form-control">
                                    <button type="button" ng-if="question.choices.length === 1 && question.choices.indexOf(choice) === 0" class="btn btn-default" ng-click="addRemoveChoice(question,choice, '+')">+</button>
                                    <button type="button" ng-if="question.choices.length > 1 && (question.choices.indexOf(choice) >= 0 && question.choices.indexOf(choice) < question.choices.length - 1)" class="btn btn-default" ng-click="addRemoveChoice(question,choice, '-')">-</button>
                                    <button type="button" ng-if="question.choices.length > 1 && question.choices.length < 5 && (question.choices.indexOf(choice) === question.choices.length - 1)" class="btn btn-default" ng-click="addRemoveChoice(question,choice, '+')">+</button>
                                    <button type="button" ng-if="question.choices.length > 1 && question.choices.length < 5 && (question.choices.indexOf(choice) === question.choices.length - 1)" class="btn btn-default" ng-click="addRemoveChoice(question,choice, '-')">-</button>
                                    <button type="button" ng-if="question.choices.length > 1 && question.choices.length === 5 && (question.choices.indexOf(choice) === question.choices.length - 1)" class="btn btn-default" ng-click="addRemoveChoice(question,choice, '-')">-</button>
                                </div>
                                <label>Answer is: {{question.answer.answerChoice}}</label>
                            </form>
                        </div>
                    </div>
                </div>
                <div>
                    <button class="btn btn-primary" ng-click="makeQuestion(addQuiz)">Add new question</button>
                </div>
                <button class="btn btn-primary" ng-click="saveQuiz(addQuiz)">Save the Quiz</button><button class="btn btn-primary" ng-click="cancelQuizCreation(addQuiz)">Cancel Quiz Creation</button>
            </div>
        </c:when>
    </c:choose>
    <div class="list-group">
        <%--The Quiz View --%>
        <div ng-repeat="quiz in quizList" class="list-group-item">
            <div class="quizCard" id="quizViewer{{$index}}">
                <h4 class="row">
                    <c:choose>
                        <%-- ONLY PROFS CAN EDIT/REMOVE--%>
                        <c:when test="${userType eq 'prof' && isOwner eq true}">
                            <p class="col-sm-9" ng-bind="quiz.title"></p>
                            <span id="createAsgmt" data-target="#quizEditor{{$index}}" data-toggle="collapse"  ng-click="editQuizInit($index,quiz)" class="btn-xs col-sm-1 glyphicon glyphicon-pencil clickable on-show"></span>
                            <span ng-click="deleteQuiz(quiz)" class="btn-xs col-sm-1 glyphicon glyphicon-trash clickable on-show"></span>
                        </c:when>
                        <%--STUDENT OR PROFESSORS VIEWING CAN SEE THIS--%>
                        <c:when test="${isOwner eq false}">
                            <a href="#" ng-click="quiztaker(quiz)" ui-sref="quizTaker" class="col-sm-9" ng-bind="quiz.title"></a>
                        </c:when>
                    </c:choose>
                </h4>
                <p ng-bind="'Maximum Grade: ' + quiz.maxGrade"></p>
                <h6 ng-bind="'Due ' + quiz.dueDate"></h6>
                <p ng-bind="Total"></p>
            </div>
            <c:choose>
                <%--IF PROFESSOR, APPLY THIS FUNCTIONALITY--%>
                <c:when test="${userType eq 'prof'}">
                    <%--QUIZ EDITOR--%>
                    <div class="collapse" id="quizEditor{{$index}}">
                        <input id="editTitle{{$index}}" type="text" placeholder="Title" class="" value="{{quiz.title}}" >
                        <input type="text" id="editMaxGrade{{$index}}" placeholder="Max Grade" value="{{quiz.maxGrade}}" >
                            <%-- DATE PICKER --%>
                        <div class="input-group date asgmt-form datepicker" id="datepicker{{$index}}" data-provide="datepicker">
                            <label for="date{{$index}}" class="col-2 col-form-label">Due Date</label>
                            <input value="{{quiz.date}}" id="date{{$index}}" required type="text" class="form-control">
                            <div class="input-group-addon">
                                <span class="glyphicon glyphicon-th"></span>
                            </div>
                        </div>
                            <%-- TIME PICKER --%>
                        <div class="input-group bootstrap-timepicker asgmt-form">
                            <label for="timepicker{{$index}}" class="col-2 col-form-label">Due Time</label>
                            <input value="{{quiz.time}}" required id="timepicker{{$index}}" type="text" class="form-control input-small timepicker">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
                        </div>
                            <%--Tag Selection and Removal section--%>
                        <div class="col-lg-12">
                            <div class="form-inline">
                                <input list="tags{{$index}}"  ng-model="quiz.edit.selectedTag" class="form-control tagList" name="tags{{$index}}">
                                <datalist id="tags{{$index}}">
                                    <option ng-repeat="tag in tagList">{{tag}}</option>
                                </datalist>
                                <button type="button" class="btn addTagBtn" ng-click="addTag(quiz.edit)">Add Tag</button>
                            </div>
                                <%--Where We add the tag chips--%>
                            <div class="tagPane">
                                    <%--The X button should remove the tag instead of hiding it--%>
                                <div class="chip" ng-repeat="quizTagged in quiz.edit.quizTaggedList">
                                    <span class="closebtn" ng-click="deleteTag(quizTagged,quiz.edit)">&times;</span>
                                    {{quizTagged}}
                                </div>
                            </div>
                        </div>
                            <%--Questions are displayed here--%>
                        <div class="questionDiv">
                            <div ng-repeat="question in quiz.edit.questionList">
                                <form class="form-group form-inline">
                                    <label for="shortAnswerMaker2{{$index}}">Short Answer</label>
                                    <input id="shortAnswerMaker2{{$index}}" ng-click="changeQuestionType(question)" type="radio" name="content" ng-model="question.type" value="ShortAns">
                                    <label for="multipleChoiceMaker2{{$index}}">Multiple Choice</label>
                                    <input id="multipleChoiceMaker2{{$index}}" ng-click="changeQuestionType(question)" type="radio" name="content" ng-model="question.type" value="M/C">
                                    <span ng-click="deleteQuestion(quiz.edit,question)" class="btn-xs glyphicon glyphicon-trash clickable"></span>
                                </form>
                                <div class="form-group form-inline">
                                    <label>Question</label>
                                    <input type="text" ng-model="question.question">
                                    <br>
                                    <div ng-if="question.type === 'ShortAns'" >
                                        <label>Question Answer</label>
                                        <input tyoe="text" ng-model="question.answer">
                                    </div>
                                    <form ng-if="question.type === 'M/C'">
                                        <div ng-repeat="choice in question.choices">
                                            <input type="radio" name="choices" ng-checked="choice.isChecked" ng-click="setMCAnswer(question,choice)" value="{{question.choices.indexOf(choice)+1|character}}">{{question.choices.indexOf(choice)+1|character}}
                                            <input type="text" ng-model="choice.answerChoice" class="form-control">
                                            <button type="button" ng-if="question.choices.length === 1 && question.choices.indexOf(choice) === 0" class="btn btn-default" ng-click="addRemoveChoice(question,choice, '+')">+</button>
                                            <button type="button" ng-if="question.choices.length > 1 && (question.choices.indexOf(choice) >= 0 && question.choices.indexOf(choice) < question.choices.length - 1)" class="btn btn-default" ng-click="addRemoveChoice(question,choice, '-')">-</button>
                                            <button type="button" ng-if="question.choices.length > 1 && question.choices.length < 5 && (question.choices.indexOf(choice) === question.choices.length - 1)" class="btn btn-default" ng-click="addRemoveChoice(question,choice, '+')">+</button>
                                            <button type="button" ng-if="question.choices.length > 1 && question.choices.length < 5 && (question.choices.indexOf(choice) === question.choices.length - 1)" class="btn btn-default" ng-click="addRemoveChoice(question,choice, '-')">-</button>
                                            <button type="button" ng-if="question.choices.length > 1 && question.choices.length === 5 && (question.choices.indexOf(choice) === question.choices.length - 1)" class="btn btn-default" ng-click="addRemoveChoice(question,choice, '-')">-</button>
                                        </div>
                                        <label>Answer is: {{question.answer.answerChoice}}</label>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div>
                            <button class="btn btn-primary" ng-click="makeQuestion(quiz.edit)">Add new question</button>
                        </div>
                        <button data-toggle="collapse" data-target="#quizEditor{{$index}}" class="btn btn-primary" ng-click="updateQuiz($index,quiz)">Save Edit</button>
                        <button data-toggle="collapse" data-target="#quizEditor{{$index}}" class="btn btn-primary" ng-click="cancelEdit($index)">Cancel Edit</button>
                    </div>
                </c:when>
            </c:choose>
        </div>
    </div>
</div>

</body>


</html>
