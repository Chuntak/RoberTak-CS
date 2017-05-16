/**
 * Created by Chuntak on 5/5/2017.
 */
var app = angular.module('homeApp');



/* FACTORY TO HANDLE HTTP REQUEST LOGIC */
app.factory('httpQuizFactory', function($http, global) {
    /* SET SINGLETON LIKE OBJECT */
    var properties = this;

    /*SAVE THE QUIZ*/
    properties.saveQuiz = function(quiz) {
        var quizQuestionList = JSON.parse(JSON.stringify(quiz.questionList));
        $.each(quizQuestionList,function() {
            if(this.type === "M/C") {
                this.answer = this.answer.answerChoice;
            }
            this.choices = angular.toJson(this.choices);
        });
        debugger;
        var parameters = { "id" : quiz.id, "title" : quiz.title, "courseId" :
            global.getCourseId(), "dueDate" : quiz.dueDate, "maxGrade" : quiz.maxGrade ,"tagNames" : quiz.quizTaggedList };
        return $http.post("/updateQuiz", JSON.parse(angular.toJson(quizQuestionList)),
            { params : parameters });
    };

    /*GET ALL QUIZ FOR THE COURSE*/
    properties.getQuiz = function() {
        var parameters = {"courseId" : global.getCourseId()};
        return $http.get("/getQuiz", { params : parameters });
    };

    /*GET QUIZ CONTENT, GIVES THE TAGS AND QUESTIONS*/
    properties.getQuizProblem = function(quizId) {
        var parameters = { "id" : quizId };
        return $http.get("/getQuizProblemsForStudent", { params : parameters });
    };

    /*SAVES THE ANSWER TO A PROBLEM*/
    properties.saveAnswer = function(problem) {
        var parameters = { problemId : problem.problemId, quizId : global.getQuizId(), answer : problem.answer };
        return $http.get("/updateStudentAnsForProbInQuiz", { params : parameters });
    };

    return properties;
});

/*MAKES INDEX INTO A B C D FOR MULTIPLE CHOICE*/
app.filter('character',function(){
    return function(input){
        return String.fromCharCode(96 + parseInt(input,10));
    };
});


app.controller('quizCtrl', function ($scope, $http, $state, global, httpQuizFactory) {
    /* TIME FORMATTING OPTIONS */
    var options = {
        weekday: "long", year: "numeric", month: "short",
        day: "numeric", hour: "2-digit", minute: "2-digit"
    };

    /* DATE FORMMATING OPTIONS */
    var dateOptions = {month: "2-digit", year: "numeric", day: "2-digit"};

    /* TIME FORMMATING OPTIONS */
    var timeOptions = { hour: "2-digit", minute: "2-digit" };

    /*Get all quizzes in the course*/
    httpQuizFactory.getQuiz().success(function(response){
        $.each(response, function() {
            this.date = new Date(this.dueDate).toLocaleDateString("en-us", dateOptions);
            this.time = new Date(this.dueDate).toLocaleTimeString("en-us", timeOptions);
            this.dueDate = new Date(this.dueDate).toLocaleTimeString("en-us", options);
        });
        $scope.quizList = response;
    }).error(function(response){ console.log("getQuiz error"); });

    $scope.quiztaker = function(quiz) {
        global.setQuizId(quiz.id);
    };

});

app.controller('quizTakCtrl',function ($scope, $http, $state, global, httpQuizFactory) {
    $scope.problemList = [];
    $scope.problemPage = 1;
    $scope.quizCompleted = false;
    var lastProblemPage = $scope.problemPage;
    $scope.itemsPerPage = 1;
    httpQuizFactory.getQuizProblem(global.getQuizId()).success(function(response){
        $scope.problemList = response;
    }).error(function(response) {
        console.log("getQuizProblem");
    });

    /*SAVE ANSWER*/
    $scope.saveAnswer = function(){
        httpQuizFactory.saveAnswer($scope.problemList[lastProblemPage-1]).success(function(response){
           lastProblemPage = $scope.problemPage;
            $scope.quizCompleted = isQuizCompleted;
            debugger;
        }).error(function(response) {
            console.log("save answer error");
        });
    };

    /*THIS CHECKS IF USER CLICKS ON ANOTHER TAB, WE'LL SAVE*/
    $scope.$on('$destroy', function onLeave() {
        /*SAVES ANSWER WHEN USER LEAVES THE TAB*/
        $scope.saveAnswer();
    });

    var isQuizCompleted = function(){
        $.each($scope.problemList, function() {
            if(this.answer !== null && this.answer !== ''){
                return false;
            }
            return false;
        });
    };

});