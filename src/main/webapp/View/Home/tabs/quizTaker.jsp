<%--
  Created by IntelliJ IDEA.
  User: Chuntak
  Date: 5/14/2017
  Time: 1:09 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Quiz Taker tab</title>
    <link href="https://netdna.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value="/resources/app/css/quiz.css" />">
</head>
<body ng-controller="quizTakCtrl">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h2 ng-if="quizNotAvaliable === false" ng-bind="'Question ' + problemPage"></h2>
            <h6 ng-if="quizNotAvaliable === false" ng-bind="'Worth ' + problemList[problemPage-1].pointsWorth + ' points'"></h6>
            <h6 ng-if="quizNotAvaliable === true" ng-bind="'Quiz Not Avaliable'"></h6>
        </div>
        <div class="panel-body content-quiz">
            <br>
            <h4 ng-bind="problemList[problemPage-1].question"></h4>
            <br>
            <textarea ng-if="problemList[problemPage-1].type === 'ShortAns'" placeholder="Enter answer here" ng-model="problemList[problemPage-1].answer" class="form-control"></textarea>
            <div ng-if="problemList[problemPage-1].type === 'M/C'">
                <div ng-repeat="choice in problemList[problemPage-1].choices">
                    <h4><input type="radio" ng-model="problemList[problemPage-1].answer" ng-value="choice.answerChoice">
                    <span ng-bind="($index+1|character) + ')  ' + (choice.answerChoice)"></span></h4>
                </div>
                <br>
                <h5 ng-bind="'Your answer is:  ' + problemList[problemPage-1].answer"></h5>
            </div>
        </div>
        <!-- /panel-body -->
        <div class="panel-footer">
            <div class="row">
                <ul ng-click="saveAnswer()"  uib-pagination total-items="problemList.length" ng-model="problemPage" max-size="10" class="pagination-sm pagination-margin col-sm-8" boundary-link-numbers="false" items-per-page="1"></ul>
                    <!-- Indicates a successful or positive action -->
                <button ng-if="quizNotAvaliable === false" class="btn btn-success col-sm-3" ng-click="submitQuizAttempt()" type="button">Submit Quiz</button>
                <button ng-if="quizNotAvaliable === true" class="btn btn-success col-sm-3" ui-sref="quiz" type="button">Go Back To Quiz</button>
            </div>
        </div>
        <!-- panel footer -->
    </div>
    <!-- /panel-primary -->
    <!-- The Modal -->
    <div id="confirmationModal" class="modal container-fluid">
        <!-- Modal content -->
        <div class="modal-content">
            <div class="modal-header">
                <a href="#" data-dismiss="modal" aria-hidden="true" class="close">×</a>
                <h4 ng-if="studentGrade < 0" ng-bind="'Continue to submit?'"></h4>
            </div>
            <div class="modal-body">
                <p ng-bind="modalMessage"></p>
            </div>
            <div class="modal-footer">
                <a href="#" ng-if="studentGrade < 0" ng-click="submitQuiz()" id="btnYes" class="btn danger">Yes</a>
                <a href="#" ng-if="studentGrade < 0" data-dismiss="modal" aria-hidden="true" class="btn secondary">No</a>
                <a href="#" id="modalFinishId" data-dismiss="modal" ui-sref="quiz" ng-if="studentGrade >= 0" class="btn secondary">Click Here To Exit</a>
            </div>
        </div>

    </div>
</body>
</html>
