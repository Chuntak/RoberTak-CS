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
            <h4 ng-bind="problemList[problemPage-1].question"></h4>
            <h6 ng-bind="'This is worth ' + problemList[problemPage-1].pointsWorth + ' points.'"></h6>
        </div>
        <div class="panel-body">
            <textarea ng-if="problemList[problemPage-1].type === 'ShortAns'" placeholder="Your answer" ng-model="problemList[problemPage-1].answer"></textarea>
            <div ng-if="problemList[problemPage-1].type === 'M/C'">
                <div ng-repeat="choice in problemList[problemPage-1].choices">
                    <input type="radio" ng-model="problemList[problemPage-1].answer" ng-value="choice.answerChoice">
                    <span ng-bind="($index+1|character) + ') ' + (choice.answerChoice)"></span>
                </div>
                <p ng-bind="'The answer you put is: ' + problemList[problemPage-1].answer"></p>
            </div>
        </div>
        <!-- /panel-body -->
        <div class="panel-footer">
            <div class="row">
                <ul ng-click="saveAnswer()"  uib-pagination total-items="problemList.length" ng-model="problemPage" max-size="10" class="pagination-sm pagination-margin col-sm-8" boundary-link-numbers="false" items-per-page="itemsPerPage"></ul>
                    <!-- Indicates a successful or positive action -->
                <button class="btn btn-success col-sm-3" ng-click="submitQuizAttempt()" type="button">Submit Quiz</button>
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
                <h4 ng-bind="'Continue to submit?'"></h4>
            </div>
            <div class="modal-body">
                <p ng-bind="confirmationModalMessage"></p>
            </div>
            <div class="modal-footer">
                <a href="#" ng-click="submitQuiz()" id="btnYes" class="btn danger">Yes</a>
                <a href="#" data-dismiss="modal" aria-hidden="true" class="btn secondary">No</a>
            </div>
        </div>

    </div>
</body>
</html>
