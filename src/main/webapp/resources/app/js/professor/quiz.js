/**
 * Created by Calvin on 5/5/2017.
 */
var app = angular.module('homeApp');
/*Grades Controller*/
app.controller('quizCtrl', function ($scope, $http, $state, global) {
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
        $scope.addQuiz.push(question);
    };

    $scope.makeQuiz = function (){
        $('#addQuizBtn').attr("disabled", true);
        $('#quizCreation').fadeToggle('fast');
        $scope.addQuiz = {title:"Untitled",courseTaggedList:[],selectedTag : "", questionList:[]};
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
        quiz.questionList.push({title:"Question "+(quiz.questionList.length+1),description:"",choices:["","","",""],answer:""});
    }

    $scope.saveQuiz = function (quiz) {
        debugger;
        $('#addQuizBtn').attr("disabled", false);
        $('#quizCreation').fadeToggle('fast');
        $scope.quizList.push(quiz);

    }
});
