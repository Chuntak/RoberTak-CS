/**
 * Created by Chuntak on 4/2/2017.
 */


var app = angular.module('signInApp', []);
app.controller('signInCtrl', function ($scope, $http) {
    var googleUser = {};
    var startApp = function () {
        gapi.load('auth2', function () {
            // Retrieve the singleton for the GoogleAuth library and set up the client.
            auth2 = gapi.auth2.init({
                client_id: '858023592805-rlit5sgi3a4mplhq1fgkk58522brusjo.apps.googleusercontent.com',
                cookiepolicy: 'single_host_origin',
                // Request scopes in addition to 'profile' and 'email'
                //scope: 'additional_scope'
            });
            attachSignin(document.getElementById('customBtn'));
        });
    };

    function attachSignin(element) {
        console.log(element.id);
        auth2.attachClickHandler(element, {},
            function (googleUser) {
                var userFirstName = googleUser.getBasicProfile().getGivenName();
                var userLastName = googleUser.getBasicProfile().getFamilyName();
                var userEmail = googleUser.getBasicProfile().getEmail();
                sessionStorage.setItem("userFirstName", userFirstName);
                sessionStorage.setItem("userLastName", userLastName);
                sessionStorage.setItem("email", userEmail);

                var y = $http({
                    method: 'GET',
                    url: '/checkUser',
                    params: {"email" : userEmail}
                }).then(function (response) {
                    debugger;
                    var isOldUser = response.data;
                    if(isOldUser === true){
                        document.forms["index"].submit();
                    } else {
                        document.forms["signUp"].submit();
                    }
                }, function errorCallBack(response) {
                    debugger;
                    alert("print\n");
                });


            }, function (error) {
                alert(JSON.stringify(error, undefined, 2));
                alert("Sign in error");
            });
    }
    startApp();
});