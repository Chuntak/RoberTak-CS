/**
 * Created by Chuntak on 4/3/2017.
 */
var app = angular.module('homeInApp', []);
app.controller('homeCtrl', function ($scope, $http) {
    $scope.userFirstName = sessionStorage.getItem("userFirstName");
});

/*
 Signs user out by redirecting to sign in page and removes session & session storage data
 When the page is fully loaded, sets the out button onclick to sign out function
 */
$(document).ready(function() {
    $("#out").click(function() {
        debugger;
        var auth2 = gapi.auth2.getAuthInstance();
        auth2.signOut().then(function () {
            sessionStorage.clear();
            console.log('User signed out.');
            document.forms["signOut"].submit();
            alert("Signed Out");
        }, function (error) {
            alert(JSON.stringify(error, undefined, 2));
        });
    });
});
