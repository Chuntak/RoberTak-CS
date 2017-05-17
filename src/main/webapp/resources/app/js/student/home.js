/**
 * Created by Chuntak on 4/3/2017.
 */

/*Angular*/
angular.module('homeApp', ["ui.router", "ui.bootstrap"]);

/* LOG OUT FUNCTION */
var logout = function(){
    /* DISPLAY SOMETHING */
    console.log('User signed out.');
    /* CLEAR THE SESSION */
    sessionStorage.clear();
    /* TRIGGER LOGOUT */
    document.forms["signOut"].submit();
};


function onLoad() {
    gapi.load('auth2', function() {
        gapi.auth2.init();
    });
}