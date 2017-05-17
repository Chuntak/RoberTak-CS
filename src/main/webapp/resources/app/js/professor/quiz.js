/**
 * Created by Calvin, Chuntak on 5/5/2017.
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

/*MAKES INDEX INTO A B C D FOR MULTIPLE CHOICE*/
app.filter('character',function(){
    return function(input){
        return String.fromCharCode(96 + parseInt(input,10));
    };
});

/*Grades Controller*/
app.controller('quizCtrl', function ($scope, $http, $state, global, httpQuizFactory) {
    $scope.quizList = [];

    /*GETS THE TAGS*/
    global.getTag().success(function(response){
        $scope.tagList = response;
    }).error(function(response) { console.log("get tag error " + response)});

    /* INIT THE DATEPICKER */
    $('#datepicker').datepicker({
        format: "mm-dd-yy"
    });
    /* INIT THE TIME */
    $('#timepicker').timepicker('showWidget');

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
        /* INIT THE TIME */
    }).error(function(response){ console.log("getQuiz error"); });
    /* Quiz is a list of questions wtih Quiz Title and Questions
     Questions have Question Number, Question Description, Question choices, Question answer
     */

    $scope.addQuestion = function (quiz, question) {
        $scope.addQuiz.questionList.push(question);
    };

    $scope.makeQuiz = function (){
        $('#addQuizBtn').attr("disabled", true);
        $('#quizCreation').fadeToggle('fast');
        $scope.addQuiz = {id:0,title:"",quizTaggedList:[],selectedTag : "", questionList:[],contentLoaded:false};
    };
    /*THIS IS TO INITIALIZE THE QUIZ EDIT*/
    $scope.editQuizInit = function (index, quiz) {
        /*GET THE QUIZ VIEWER*/
        var x = document.getElementById("quizViewer" + index);
        if (x.style.display === 'none') {
            /*SHOW THE QUIZ VIEWER*/
            x.style.display = 'initial';
        } else {
            /*HIDE THE QUIZ VIEWER*/
            x.style.display = 'none';
            if(quiz.contentLoaded === "almost") {
                /* INIT THE DATEPICKER */
                $('#datepicker' + index).datepicker({format: "mm-dd-yy"});
                /* INIT THE TIME */
                $('#timepicker' + index).timepicker();
                quiz.contentLoaded = true;
            }
            if (!quiz.contentLoaded) {
                /* INIT THE DATEPICKER */
                $('#datepicker' + index).datepicker({format: "mm-dd-yy"});
                /* INIT THE TIME */
                $('#timepicker' + index).timepicker();
                httpQuizFactory.getQuizContent(quiz).success(function (response) {
                    quiz.contentLoaded = true;
                    quiz.quizTaggedList = response.quizTaggedList;
                    quiz.questionList = response.questionList;
                    /*EDIT BUT NOT COMMIT*/
                    quiz.edit = {
                        quizTaggedList: JSON.parse(JSON.stringify(quiz.quizTaggedList)),
                        questionList: JSON.parse(JSON.stringify(quiz.questionList)),
                        deletedQuestionList : [],
                        selectedTag : ""
                    };
                    /*SELECT THE ANSWER FOR EACH M/C*/
                    $.each(quiz.edit.questionList, function(index){
                        if(this.type === "M/C") {
                            var i = index;
                            var question = this;
                            $.each(this.choices, function(index) {
                                if(question.answer === this.answerChoice) {
                                    this.isChecked = true;
                                    question.answer = this;
                                }
                            });
                        }
                    });
                }).error(function (response) {
                    console.log("getQuizContentError");
                })
            } else {
                /*EDIT BUT NOT COMMIT*/
                quiz.edit = {
                    quizTaggedList: JSON.parse(JSON.stringify(quiz.quizTaggedList)),
                    questionList: JSON.parse(JSON.stringify(quiz.questionList)),
                    deletedQuestionList : []
                };
                /*SELECT THE ANSWER FOR EACH M/C*/
                $.each(quiz.edit.questionList, function(index){
                    if(this.type === "M/C") {
                        var i = index;
                        var question = this;
                        $.each(this.choices, function(index) {
                            if(question.answer === this.answerChoice) {
                                this.isChecked = true;
                                question.answer = this;
                            }
                        });
                    }
                });
            }
        }
        document.getElementById("editTitle" + index).value = quiz.title;
        document.getElementById("date" + index).value = quiz.date;
        document.getElementById("timepicker" + index).value = quiz.time;
        document.getElementById("editMaxGrade" + index).value = quiz.maxGrade;
    };

    /*ADD TAG */
    $scope.addTag = function(quiz){
        if(quiz.quizTaggedList.indexOf(quiz.selectedTag) === -1 && quiz.selectedTag !== ""){
            quiz.quizTaggedList.push(quiz.selectedTag);
        }
        quiz.selectedTag = "";
    };

    $scope.deleteTag = function(quizTagged,quiz) {
        for(var i = 0; i < quiz.quizTaggedList.length; i++){
            if(quiz.quizTaggedList[i] === quizTagged){
                quiz.quizTaggedList.splice(i,1);
                break;
            }
        }
    };

    $scope.makeQuestion = function (quiz) {
        quiz.questionList.push({question:"",answer:"", deleted:false, type:"ShortAns",
            choices:[{id : 0, questionId : 0, answerChoice : "", answerLetter : ""}]});
    };


    $scope.addRemoveChoice = function(question, choice, x) {
        if(x === '+') {
            question.choices.push({id : 0, questionId : 0, answerChoice : "", answerLetter : ""});
        }else {
            var i = question.choices.indexOf(choice);
            question.choices.splice(i, 1);
            if(question.answer === choice) {
                question.answer = {};
            }
        }
    };

    $scope.setMCAnswer = function(question,choice) {
        question.answer = choice;
    };

    $scope.deleteQuestion = function (quiz, question) {
        var x = quiz.questionList.indexOf(question);
        quiz.questionList.splice(x, 1);
        question.deleted = true;
        if(quiz.deletedQuestionList) quiz.deletedQuestionList.push(question);
    };

    /*THIS IS TO CLEAR THEM OUT*/
    $scope.changeQuestionType = function(question) {
        if(question.type === "M/C") {
            question.choices = [{id : 0, questionId : 0, answerChoice : "", answerLetter : ""}];
            question.answer = "";
            question.question = "";
        } else if (question.type === "ShortAns") {
            question.choices = [];
            question.answer = "";
            question.question = "";
        }
    };

    $scope.saveQuiz = function (quiz) {
        $('#addQuizBtn').attr("disabled", false);
        $('#quizCreation').fadeToggle('fast');
        quiz.dueDate = new Date(quiz.date + " " + quiz.time).getTime();
        httpQuizFactory.saveQuiz(quiz).success(function(response){
            var time = quiz.time;
            var date = quiz.date;
            quiz = response;
            quiz.dueDate = new Date(quiz.dueDate).toLocaleTimeString("en-us", options);
            quiz.time = time;
            quiz.date = date;
            $scope.quizList.push(quiz);
            quiz.contentLoaded = "almost";
        }).error(function(response){
            console.log("saveQuiz error")
        });
    };

    $scope.deleteQuiz = function(quiz) {
        httpQuizFactory.deleteQuiz(quiz).success(function(response) {
            var x = $scope.quizList.indexOf(quiz);
            $scope.quizList.splice(x, 1);
        }).error(function(response) { console.log("delete Quiz error.")})
    };

    $scope.cancelQuizCreation = function(){
        $scope.addQuiz = {};
        $('#addQuizBtn').attr("disabled", false);
        $('#quizCreation').fadeToggle('fast');
    };

    /* SAVES EDIT */
    $scope.updateQuiz = function(index, quiz) {
        quiz.questionList = quiz.edit.questionList;
        quiz.questionList = quiz.questionList.concat(quiz.edit.deletedQuestionList);
        quiz.quizTaggedList = quiz.edit.quizTaggedList;
        quiz.title = document.getElementById("editTitle" + index).value;
        quiz.date = document.getElementById("date" + index).value;
        quiz.time = document.getElementById("timepicker" + index).value;
        quiz.maxGrade = document.getElementById("editMaxGrade" + index).value;
        quiz.dueDate = new Date(quiz.date + " " + quiz.time).getTime();
        httpQuizFactory.saveQuiz(quiz).success(function(response){
            quiz.questionList = response.questionList;
            quiz.dueDate = new Date(quiz.date + " " + quiz.time).toLocaleTimeString("en-us", options);
        }).error(function(response) {
            console.log("Update Quiz error");
        });

        /*PUT BACK THE DISPLAY ON*/
        document.getElementById("quizViewer" + index).style.display = 'initial';
    };

    /* CANCEL EDITING, UPLOAD BACK TO ORIGINAL INFORMATION TO THE TEXTBOX */
    $scope.cancelEdit = function(index){
        /*PUT BACK THE DISPLAY ON*/
        document.getElementById("quizViewer" + index).style.display = 'initial';
    };

});
