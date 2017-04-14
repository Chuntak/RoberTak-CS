/**
 * Created by Chuntak on 4/3/2017.
 */

/*Angular*/
angular.module('homeApp', ["ui.router"]);

/*Home Controller*/
angular.module('homeApp').controller('homeCtrl', function ($scope, $http) {
    $scope.userFirstName = sessionStorage.getItem("userFirstName");
    $scope.signOut = function() {
        console.log('User signed out.');
        sessionStorage.clear();
        document.forms["signOut"].submit();
    };
});

function onLoad() {
    gapi.load('auth2', function() {
        debugger;
        gapi.auth2.init();
    });
}