<%--
  Created by IntelliJ IDEA.
  User: Chuntak
  Date: 5/14/2017
  Time: 1:09 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Quiz Taker tab</title>
    <link href="https://netdna.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
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
            <ul ng-click="saveAnswer()" uib-pagination total-items="problemList.length" ng-model="problemPage" max-size="10" class="pagination-sm" boundary-link-numbers="false" items-per-page="itemsPerPage"></ul>
        </div>
        <!-- panel footer -->
    </div>
    <!-- /panel-primary -->
</body>
</html>
