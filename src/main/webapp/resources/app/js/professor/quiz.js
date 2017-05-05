/**
 * Created by Calvin on 5/5/2017.
 */
var app = angular.module('homeApp');
/*Grades Controller*/
app.controller('quizCtrl', function ($scope, $http, $state, global) {
    $scope.quizList = [];

    /*Get all quizzes in the course*/

    /* Quiz is a list of questions wtih Quiz Title and Questions
     Questions have Question Number, Question Description, Question choices, Question answer



     */

    /*Adds a quiz to the quizList*/
    $scope.addQuiz = function(quiz){
        $scope.quizList.push(quiz)
    };

    $scope.quiz = {};
    $scope.addQuestion = function (quiz, question) {
        $scope.quiz.push(question);
    };

    $scope.makeQuiz = function (){
        debugger;
        $scope.quizList.push({title:"Untitled",courseTaggedList:[],selectedTag : "", questionList:[]});
    };

    /*ADD TAG */
    $scope.addTag = function(quiz){
        debugger;
        if(quiz.courseTaggedList.indexOf(quiz.selectedTag) === -1 && quiz.selectedTag !== ""){
            quiz.courseTaggedList.push(quiz.selectedTag);
        }
        quiz.selectedTag = "";
    };

    $scope.removeTag = function(courseTagged,quiz) {
        debugger;
        for(var i = 0; i < quiz.courseTaggedList.length; i++){
            if(quiz.courseTaggedList[i] === courseTagged){
                quiz.courseTaggedList.splice(i,1);
                break;
            }
        }
    };

    $scope.addQuestion = function (quiz) {
        debugger;
        quiz.questionList.push({title:"",description:"",choices:["","","",""],answer:""});
    }

});
