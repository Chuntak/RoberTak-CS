<%--
  Created by IntelliJ IDEA.
  User: Calvin, Chuntak, Robert, Susan
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
            <div class="quiz-create">

                <%--CREATE QUIZ BUTTON--%>
                <div class="list-group-item-success">
                    <h4 class="panel-title" id="panel">
                        <a ng-click="makeQuiz()" id="addQuizBtn">Create a Quiz</a>
                    </h4>
                </div>

                <%--CREATE A QUIZ--%>
                <div id="quizCreation" ng-model="addQuiz" class="form-group quiz-form">

                    <%-- QUIZ TITLE --%>
                    <div class="col-xs-6">
                        <label>Title: </label>
                        <input type="text" placeholder="Title" class="form-control" ng-model="addQuiz.title" required />
                    </div>

                    <%-- MAXIMUM GRADE--%>
                    <div class="col-xs-6">
                        <label>Max Grade: </label>
                        <input type="number" placeholder="Maximum Grade" class="form-control" ng-model="addQuiz.maxGrade" required />
                    </div>

                    <%-- DATE PICKER --%>
                    <div class="col-xs-6">
                        <label class="label-padding">Due Date: </label>
                        <div class="input-group date quiz-form" id="datepicker" data-provide="datepicker">
                            <label for="date" class="col-2 col-form-label"></label>
                            <input ng-model="addQuiz.date" id="date" required type="text" class="form-control">
                            <div class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar "></span>
                            </div>
                        </div>
                    </div>

                    <%-- TIME PICKER --%>
                    <div class="col-xs-6">
                        <label class="label-padding">Due Time: </label>
                        <div class="input-group bootstrap-timepicker quiz-form">
                            <label for="timepicker" class="col-2 col-form-label"></label>
                            <input ng-model="addQuiz.time" required id="timepicker" type="text" class="form-control input-small timepicker">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
                        </div>
                    </div>

                    <%--Tag Selection and Removal section--%>
                    <div class="col-lg-12">
                        <div class="form-inline">
                            <input list="tags"  ng-model="addQuiz.selectedTag" class="form-control tagList" name="tags">
                            <datalist id="tags">
                                <option ng-repeat="tag in tagList">{{tag}}</option>
                            </datalist>
                            <button type="button" class="btn btn-success" ng-click="addTag(addQuiz)">Add Tag</button>
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

                            <%-- SELECT FROM CHOICE OF SHORT QUESTION OR MULTIPLE CHOICE --%>
                            <form class="form-group col-xs-12">
                                <hr>
                                <label for="shortAnswerMaker{{$index}}">Short Answer </label>
                                <input id="shortAnswerMaker{{$index}}" ng-click="changeQuestionType(question)" type="radio" name="content" ng-model="question.type" value="ShortAns">&nbsp&nbsp
                                <label for="multipleChoiceMaker{{$index}}">Multiple Choice </label>
                                <input id="multipleChoiceMaker{{$index}}" ng-click="changeQuestionType(question)" type="radio" name="content" ng-model="question.type" value="M/C">
                                <span ng-click="deleteQuestion(addQuiz,question)" class="btn-xs glyphicon glyphicon-remove clickable remove"></span>
                            </form>
                            <div class="form-group">
                                <div class="col-xs-1"><label>{{$index+1}}. </label></div>
                                <div class="col-xs-11">
                                    <label>Enter Question: </label>
                                    <textarea type="text" class="form-control textarea-style" ng-model="question.question" placeholder="Write question here" required></textarea>
                                    <br>
                                    <div ng-if="question.type === 'ShortAns'" >
                                        <label>Question Answer: </label>
                                        <textarea tyoe="text" ng-model="question.answer" class="form-control" placeholder="Write answer here"></textarea>
                                    </div>
                                    <form ng-if="question.type === 'M/C'">
                                        <div ng-repeat="choice in question.choices" class="form-inline">
                                            <input type="radio" required name="choices" ng-click="setMCAnswer(question,choice)" value="{{question.choices.indexOf(choice)+1|character}}">  {{question.choices.indexOf(choice)+1|character}}.
                                            <input type="text" id="multipleChoiceQuestion0{{$parent.$index}}{{$index}}" ng-model="choice.answerChoice" class="form-control">

                                            <button type="button" ng-if="question.choices.length === 1 && question.choices.indexOf(choice) === 0" class="btn btn-default" ng-click="addRemoveChoice(question,choice, '+')">+</button>
                                            <button type="button" ng-if="question.choices.length > 1 && (question.choices.indexOf(choice) >= 0 && question.choices.indexOf(choice) < question.choices.length - 1)" class="btn btn-default" ng-click="addRemoveChoice(question,choice, '-')">-</button>
                                            <button type="button" ng-if="question.choices.length > 1 && question.choices.length < 5 && (question.choices.indexOf(choice) === question.choices.length - 1)" class="btn btn-default" ng-click="addRemoveChoice(question,choice, '+')">+</button>
                                            <button type="button" ng-if="question.choices.length > 1 && question.choices.length < 5 && (question.choices.indexOf(choice) === question.choices.length - 1)" class="btn btn-default" ng-click="addRemoveChoice(question,choice, '-')">-</button>
                                            <button type="button" ng-if="question.choices.length > 1 && question.choices.length === 5 && (question.choices.indexOf(choice) === question.choices.length - 1)" class="btn btn-default" ng-click="addRemoveChoice(question,choice, '-')">-</button>

                                        </div><br>

                                        <%-- PROFESSOR SELECTS THE ANSWER FROM THE CHOICES INPUTTED --%>
                                        <label>Selected answer is:</label> {{question.answer.answerChoice}}
                                    </form>

                                    <%-- PROFESSOR ENTER POINT VALUE FOR THIS QUESTION --%>
                                    <div class="form-inline"><br>
                                        <label>Point Value:  </label><input type="number" class="form-control" ng-model="question.pointsWorth" placeholder="Point Value"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div>
                        <div class="col-xs-12">
                            <br>
                            <button class="btn btn-default" ng-click="makeQuestion(addQuiz)">Add new question</button>
                        </div>
                    </div>
                    <div class="quiz-btns">
                        <button class="btn btn-default" ng-click="saveQuiz(addQuiz)">Save the Quiz</button>
                        <button class="btn btn-default" ng-click="cancelQuizCreation(addQuiz)">Cancel Quiz Creation</button>
                    </div>
                </div>
            </c:when>
        </c:choose>
        <div class="list-group">
            <%--The Quiz View --%>
            <div ng-repeat="quiz in quizList" class="list-group-item quiz">
                <div class="quizCard" id="quizViewer{{$index}}">

                    <c:choose>
                        <%-- ONLY PROFS CAN EDIT/REMOVE--%>
                        <c:when test="${userType eq 'prof' && isOwner eq true}">
                            <h3 ng-bind="quiz.title"></h3>
                            <btn ng-click="deleteQuiz(quiz)" class="badge btn-xs col-sm-1 glyphicon glyphicon-trash clickable on-show"></btn>
                            <btn data-target="#quizEditor{{$index}}" data-toggle="collapse"  ng-click="editQuizInit($index,quiz)" class="badge btn-xs col-sm-1 glyphicon glyphicon-pencil clickable on-show"></btn>
                        </c:when>

                        <%--STUDENT OR PROFESSORS VIEWING CAN SEE THIS--%>
                        <c:when test="${isOwner eq false}">
                            <h3><a href="#" ng-click="quiztaker(quiz)" ui-sref="quizTaker"ng-bind="quiz.title"></a></h3>
                        </c:when>
                    </c:choose>
                    <%-- DISPLAYS DESCRIPTION OF ASSIGNMENT --%>
                    <h6 ng-bind="'Due ' + quiz.dueDate"></h6>
                    <h6 ng-bind="'Maximum Grade: ' + quiz.maxGrade"></h6>
                </div>

                <c:choose>
                    <%--IF PROFESSOR, APPLY THIS FUNCTIONALITY--%>
                    <c:when test="${userType eq 'prof'}">
                        <%--QUIZ EDITOR--%>
                        <div class="collapse" id="quizEditor{{$index}}">

                                <%-- QUIZ TITLE --%>
                            <div class="col-xs-6">
                                <label>Title: </label>
                                <input id="editTitle{{$index}}" type="text" placeholder="Title" class="form-control" value="{{quiz.title}}" required/>
                            </div>

                                <%-- MAXIMUM GRADE --%>
                            <div class="col-xs-6">
                                <label>Maximum Grade: </label>
                                <input type="number" id="editMaxGrade{{$index}}" placeholder="Maximum Grade" value="{{quiz.maxGrade}}" class="form-control" required/>
                            </div>

                                <%-- DATE PICKER --%>
                            <div class="col-xs-6">
                                <label class="label-padding">Due Date: </label>
                                <div class="input-group date quiz-form datepicker" id="datepicker{{$index}}" data-provide="datepicker">
                                    <label for="date{{$index}}" class="col-2 col-form-label"></label>
                                    <input value="{{quiz.date}}" id="date{{$index}}" required type="text" class="form-control">
                                    <div class="input-group-addon">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                    </div>
                                </div>
                            </div>

                                <%-- TIME PICKER --%>
                            <div class="col-xs-6">
                                <label class="label-padding">Due Time: </label>
                                <div class="input-group bootstrap-timepicker quiz-form">
                                    <label for="timepicker{{$index}}" class="col-2 col-form-label"></label>
                                    <input value="{{quiz.time}}" required id="timepicker{{$index}}" type="text" class="form-control input-small timepicker">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
                                </div>
                            </div>

                            <%--Tag Selection and Removal section--%>
                            <div class="col-xs-12">
                                <div class="form-inline">
                                    <input list="tags{{$index}}"  ng-model="quiz.edit.selectedTag" class="form-control tagList" name="tags{{$index}}">
                                    <datalist id="tags{{$index}}">
                                        <option ng-repeat="tag in tagList">{{tag}}</option>
                                    </datalist>
                                    <button type="button" class="btn addTagBtn" ng-click="addTag(quiz.edit)">Add Tag</button>
                                </div>
                            </div>

                            <%--Where We add the tag chips--%>
                            <div class="tagPane">
                                    <%--The X button should remove the tag instead of hiding it--%>
                                <div class="chip" ng-repeat="quizTagged in quiz.edit.quizTaggedList">
                                    <span class="closebtn" ng-click="deleteTag(quizTagged,quiz.edit)">&times;</span>
                                    {{quizTagged}}
                                </div>
                            </div>

                            <%--Questions are displayed here--%>
                            <div class="questionDiv">
                                <div ng-repeat="question in quiz.edit.questionList">
                                    <form class="form-group col-xs-12">
                                        <hr>
                                        <label for="shortAnswerMaker2{{$index}}">Short Answer</label>
                                        <input id="shortAnswerMaker2{{$index}}" ng-click="changeQuestionType(question)" type="radio" name="content" ng-model="question.type" value="ShortAns">&nbsp&nbsp
                                        <label for="multipleChoiceMaker2{{$index}}">Multiple Choice</label>
                                        <input id="multipleChoiceMaker2{{$index}}" ng-click="changeQuestionType(question)" type="radio" name="content" ng-model="question.type" value="M/C">
                                        <span ng-click="deleteQuestion(quiz.edit,question)" class="btn-xs glyphicon glyphicon-remove clickable remove"></span>
                                    </form>
                                    <div class="form-group">
                                        <div class="col-xs-1"><label>{{$index+1}}. </label></div>
                                        <div class="col-xs-11">
                                            <label>Enter Question: </label>
                                            <textarea type="text" class="form-control textarea-style" ng-model="question.question" placeholder="Write question here" required></textarea>
                                            <br>
                                            <div ng-if="question.type === 'ShortAns'" >
                                                <label>Question Answer: </label>
                                                <textarea tyoe="text" ng-model="question.answer" class="form-control" placeholder="Write answer here"></textarea>
                                            </div>
                                            <form ng-if="question.type === 'M/C'">
                                                <div ng-repeat="choice in question.choices" class="form-inline">
                                                    <input type="radio" required name="choices" ng-click="setMCAnswer(question,choice)" value="{{question.choices.indexOf(choice)+1|character}}">  {{question.choices.indexOf(choice)+1|character}}.
                                                    <input type="text" id="multipleChoiceQuestion0{{$parent.$index}}{{$index}}" ng-model="choice.answerChoice" class="form-control">

                                                    <button type="button" ng-if="question.choices.length === 1 && question.choices.indexOf(choice) === 0" class="btn btn-default" ng-click="addRemoveChoice(question,choice, '+')">+</button>
                                                    <button type="button" ng-if="question.choices.length > 1 && (question.choices.indexOf(choice) >= 0 && question.choices.indexOf(choice) < question.choices.length - 1)" class="btn btn-default" ng-click="addRemoveChoice(question,choice, '-')">-</button>
                                                    <button type="button" ng-if="question.choices.length > 1 && question.choices.length < 5 && (question.choices.indexOf(choice) === question.choices.length - 1)" class="btn btn-default" ng-click="addRemoveChoice(question,choice, '+')">+</button>
                                                    <button type="button" ng-if="question.choices.length > 1 && question.choices.length < 5 && (question.choices.indexOf(choice) === question.choices.length - 1)" class="btn btn-default" ng-click="addRemoveChoice(question,choice, '-')">-</button>
                                                    <button type="button" ng-if="question.choices.length > 1 && question.choices.length === 5 && (question.choices.indexOf(choice) === question.choices.length - 1)" class="btn btn-default" ng-click="addRemoveChoice(question,choice, '-')">-</button>
                                                </div>
                                                <br>

                                                <%-- PROFESSOR SELECTS THE ANSWER FROM THE CHOICES INPUTTED --%>
                                                <label>Selected answer is:</label> {{question.answer.answerChoice}}
                                            </form>

                                            <%-- PROFESSOR ENTER POINT VALUE FOR THIS QUESTION --%>
                                            <div class="form-inline"><br>
                                                <label>Point Value:  </label> <input type="number" class="form-control" ng-model="question.pointsWorth" placeholder="Point Value"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div>
                                <div class="col-xs-12">
                                    <br>
                                    <button class="btn btn-default" ng-click="makeQuestion(quiz.edit)">Add new question</button>
                                </div>
                            </div>
                            <div class="quiz-btns">
                                <button data-toggle="collapse" data-target="#quizEditor{{$index}}" class="btn btn-default" ng-click="updateQuiz($index,quiz)">Save Edit</button>
                                <button data-toggle="collapse" data-target="#quizEditor{{$index}}" class="btn btn-default" ng-click="cancelEdit($index)">Cancel Edit</button>
                            </div>
                        </div>
                    </c:when>
                </c:choose>
            </div>
        </div>
    </div>

</body>


</html>










