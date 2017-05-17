/**
 * Created by Chuntak on 4/3/2017.
 */

/*Angular*/
angular.module('homeApp', ["ui.router", "kendo.directives"]);

/* LOG OUT FUNCTION */
var logout = function(){
    /* DISPLAY SOMETHING */
    console.log('User signed out.');
    /* CLEAR THE SESSION */
    sessionStorage.clear();
    /* TRIGGER LOGOUT */
    document.forms["signOut"].submit();
}

function onLoad() {
    gapi.load('auth2', function() {
        gapi.auth2.init();
    });
}

/*sharing scope variable*/
angular.module('homeApp').factory('global', function() {
    var properties = { courseId : 0 };
    function getCourseId() {
        return properties.courseId;
    }
    function setCourseId(crsId) {
        properties.courseId = crsId;
    }
    return {
        getCourseId : getCourseId,
        setCourseId : setCourseId
    };
});