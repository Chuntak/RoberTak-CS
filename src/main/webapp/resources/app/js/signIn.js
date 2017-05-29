/**
 * Created by Chuntak on 4/2/2017.
 */
var app = angular.module('signInApp', []);
app.controller('signInCtrl', function ($scope, $http) {
    /* CHECK IF USER IS LOGGED IN ALREADY */
    if(sessionStorage.length > 0) {
        document.forms["index"].submit();
    }
    //INITIALIZATION
    var googleUser = {};

    //BEGIN AUTHORIZATION WITH SIGNIN
    var startApp = function () {
        gapi.load('auth2', function () {
            // Retrieve the singleton for the GoogleAuth library and set up the client.
            auth2 = gapi.auth2.init({
                //GET AUTHORIZATION
                client_id: '549270371532-7hnbnbau4mrddu52vmvinh8adj900e4v.apps.googleusercontent.com',
                cookiepolicy: 'single_host_origin',
                // Request scopes in addition to 'profile' and 'email'
                //scope: 'additional_scope'
            });
            //GET ELEMENT ID FROM JSP
            attachSignin(document.getElementById('customBtn'));
        });
    };

    //CHECK AND SENT REQUEST TO SIGNIN
    function attachSignin(element) {
        console.log(element.id);
        auth2.attachClickHandler(element, {},

            //GET USER'S DESCRIPTIONS
            function (googleUser) {
                var userFirstName = googleUser.getBasicProfile().getGivenName();
                var userLastName = googleUser.getBasicProfile().getFamilyName();
                var userEmail = googleUser.getBasicProfile().getEmail();

                //STORE VALUE TO SESSION VARIABLES
                sessionStorage.setItem("userFirstName", userFirstName);
                sessionStorage.setItem("userLastName", userLastName);
                sessionStorage.setItem("email", userEmail);

                //SENT REQUEST TO CONTROLLER TO SIGNIN
                var y = $http({
                    method: 'GET',
                    url: '/checkUser',
                    params: {"email" : userEmail}

                //RESPONSE FROM CONTROLLER
                }).then(function (response) {
                    var isOldUser = response.data;

                    //CHECK FOR OLD OR NEW USER
                    if(isOldUser === true){
                        document.forms["index"].submit();
                    } else {
                        document.forms["signUp"].submit();
                    }
                }, function errorCallBack(response) {
                });
            }, function (error) {
            });
    }
    startApp();
});