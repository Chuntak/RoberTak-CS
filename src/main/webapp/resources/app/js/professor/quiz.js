/**
 * Created by Calvin, Chuntak on 5/5/2017.
 */
/* FACTORY TO HANDLE HTTP REQUEST LOGIC */
angular.module('homeApp').factory('httpQuizFactory', function($http, global) {
    /* SET SINGLETON LIKE OBJECT */
    var properties = this;

    /*SAVE THE QUIZ*/
    properties.saveQuiz = function(quiz) {
        debugger;
        var parameters = { "id" : quiz.id, "title" : quiz.title, "courseId" :
            global.getCourseId(),  "tagNames" : quiz.quizTaggedList, "problemList" : quiz.questionList };
        return $http.get("/updateQuiz", { params : parameters} );
    };


    return properties;
});


var app = angular.module('homeApp');
/*Grades Controller*/
app.controller('quizCtrl', function ($scope, $http, $state, global, httpQuizFactory) {
    $scope.quizList = [];

    /* INIT THE DATEPICKER */
    $('#datepicker').datepicker({
        format: "mm-dd-yy"
    });
    /* INIT THE TIME */
    $('#timepicker1').timepicker();

    /*Get all quizzes in the course*/

    /* Quiz is a list of questions wtih Quiz Title and Questions
     Questions have Question Number, Question Description, Question choices, Question answer



     */

    $scope.addQuestion = function (quiz, question) {
        $scope.addQuiz.questionList.push(question);
    };

    $scope.makeQuiz = function (){
        $('#addQuizBtn').attr("disabled", true);
        $('#quizCreation').fadeToggle('fast');
        $scope.addQuiz = {id:0,title:"Untitled",quizTaggedList:[],selectedTag : "", questionList:[]};
    };

    /*ADD TAG */
    $scope.addTag = function(quiz){
        debugger;
        if(quiz.quizTaggedList.indexOf(quiz.selectedTag) === -1 && quiz.selectedTag !== ""){
            quiz.quizTaggedList.push(quiz.selectedTag);
        }
        quiz.selectedTag = "";
    };

    $scope.removeTag = function(quizTagged,quiz) {
        debugger;
        for(var i = 0; i < quiz.quizTaggedList.length; i++){
            if(quiz.quizTaggedList[i] === quizTagged){
                quiz.TaggedList.splice(i,1);
                break;
            }
        }
    };

    $scope.makeQuestion = function (quiz) {
        quiz.questionList.push({title:"Question "+(quiz.questionList.length+1), question:"",answer:""});
    };

    $scope.saveQuiz = function (quiz) {
        debugger;
        $('#addQuizBtn').attr("disabled", false);
        $('#quizCreation').fadeToggle('fast');
        //quiz.dueDate = new Date(quiz.date + " " + quiz.time).getTime();
        quiz.backUp = {title:quiz.title , quizTaggedList: quiz.quizTaggedList,
            selectedTag: quiz.selectedTag, questionList: quiz.questionList,date:quiz.date, time: quiz.time};
        $scope.quizList.push(quiz);

    };

    $scope.cancelQuizCreation = function(){
        $scope.addQuiz = {};
        $('#addQuizBtn').attr("disabled", false);
        $('#quizCreation').fadeToggle('fast');
    };

    $scope.updateQuiz = function(quiz) {
        debugger;

    };

    $scope.cancelEdit = function(quiz){
        debugger;
    };
});
