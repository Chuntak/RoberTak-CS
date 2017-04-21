/**
 * Created by rvtru on 4/21/2017.
 */
/**
 * Created by rvtru on 4/20/2017.
 */

/*sharing scope variable*/
angular.module('homeApp').factory('global', function($http) {
    var properties = this;
    properties.courseId = 0;
    //properties.posts = [];

    properties.getCourseId = function() {
        return properties.courseId;
    }
    properties.setCourseId = function(crsId) {
        properties.courseId = crsId;
    }
    // properties.getPosts = function(postId){
    //     $http.get("/getPost", {
    //         params : {
    //             "crsId" : properties.courseId,
    //             "id": 0
    //         }
    //     }).success(function(response){
    //         properties.posts = response;
    //         debugger;
    //     }).error(function(response){
    //         debugger;
    //     });
    // }
    return properties;
});