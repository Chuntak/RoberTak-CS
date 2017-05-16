/**
 * Created by rvtru on 4/21/2017.
 */
/**
 * Created by rvtru on 4/20/2017.
 */

/* global DIRECTIVE SHARED AMONG ALL CONTROLLERS */
angular.module('homeApp').factory('global', function($http) {
    /* THE DIRECTIVE */
    var properties = this;
    /* INIT CURRENT COURSE ID */
    properties.courseId = 0;
    /* INIT CURRENT USER ID */
    properties.userId = 0;
    /* INIT QUIZ ID THAT THE STUDENT IS TAKING*/
    properties.quizId = 0;

    /* RETURNS THE QUIZ ID */
    properties.getQuizId = function() {
        return properties.quizId;
    };

    /* RETURNS THE QUIZ ID */
    properties.setQuizId = function(quizId) {
        properties.quizId = quizId;
    };

    /* RETURNS COURSE ID */
    properties.getCourseId = function() {
        return properties.courseId;
    };
    /* SETS COURSE ID */
    properties.setCourseId = function(crsId) {
        properties.courseId = crsId;
    };
    /* GETS USER ID */
    properties.getUserId = function(){
        return properties.userId;
    };
    /* SETS USER ID */
    properties.setUserId = function(userId) {
        properties.userId = userId;
    };
    /*GETS THE TAGS FROM DATABASE FOR SELECTION*/
    properties.getTag = function() {
        return $http.get("/getTag");
    };
    /* RETURN DIRECTIVE */
    return properties;
});

