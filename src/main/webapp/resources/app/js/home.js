/**
 * Created by Chuntak on 4/3/2017.
 */

/*Angular*/
var app = angular.module('homeInApp', []);

/*Home Controller*/
app.controller('homeCtrl', function ($scope, $http) {
    $scope.userFirstName = sessionStorage.getItem("userFirstName");
    $scope.signOut = function() {
        console.log('User signed out.');
        sessionStorage.clear();
        document.forms["signOut"].submit();
    };
});


/*Course Controller*/
app.controller('courseCtrl', function ($scope, $http) {
    $scope.course = null;

    $scope.addCourse = function(){
        var y = $http({
            method: 'GET',
            url: '/addCourse',
            params: {"coursePrefix": $scope.course.prefix, "courseNumber":$scope.course.number, "courseName":$scope.course.name,
                "semester": $scope.course.semester, "pub": $scope.course.public }
        }).then(function (response) {
            debugger;
            $scope.course.code = response.data;
        }, function errorCallBack(response) {
            alert("add course error\n");
        });
    };
});

/*
 Signs user out by redirecting to sign in page and removes session & session storage data
 When the page is fully loaded, sets the out button onclick to sign out function
 */
// $(document).ready(function() {
//     $("#out").click(function() {
//         var auth2 = gapi.auth2.getAuthInstance();
//         auth2.signOut().then(function () {
//             sessionStorage.clear();
//             console.log('User signed out.');
//             document.forms["signOut"].submit();
//             alert("Signed Out");
//         }, function (error) {
//             alert(JSON.stringify(error, undefined, 2));
//         });
//     });
// });
