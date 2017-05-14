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

    /*get QUIZ CONTENT, GIVES THE TAGS AND QUESTIONS*/
    properties.getQuizContent = function(quiz) {
        var parameters = { "id" : quiz.id };
        return $http.get("/getQuizContent", { params : parameters });
    };

    /* DELETE QUIZ*/
    properties.deleteQuiz = function(quiz) {
        var parameters = { "id" : quiz.id };
        return $http.get("/deleteQuiz", {params : parameters});
    };

    return properties;
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
            this.contentLoaded = false; /*MARK THEM AS QUESTIONS NOT LOADED*/
        });
        $scope.quizList = response;
        debugger;
    }).error(function(response){ console.log("getQuiz error"); });

});