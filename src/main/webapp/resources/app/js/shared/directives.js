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
    };
    properties.setCourseId = function(crsId) {
        properties.courseId = crsId;
    };
    /*GETS THE TAGS FROM DATABASE FOR SELECTION*/
    properties.getTag = function() {
        return $http.get("/getTag");
    };

    return properties;
});

