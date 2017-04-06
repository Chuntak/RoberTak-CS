/**
 * Created by Calvin on 4/1/2017.
 */

$(document).ready(function() {
    // Get the modal
    var modal = document.getElementById('courseModal');
// Get the button that opens the modal
    var btn = document.getElementById("addCourse");
    var submit = document.getElementById("courseSubmit");
// Get the <span> element that closes the modal
    var span = document.getElementsByClassName("close")[0];
// When the user clicks on the button, open the modal
    btn.onclick = function() {
        modal.style.display = "block";
    };
// When the user clicks on the submit button, close the modal
    submit.onclick = function() {
        modal.style.display = "none";
    };
// When the user clicks on <span> (x), close the modal
    span.onclick = function() {
        modal.style.display = "none";
    };
// When the user clicks anywhere outside of the modal, close it
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    };
});


/*Course Controller*/
angular.module('homeApp').controller('courseCtrl', function ($scope, $http) {
    $scope.course = "";
    $scope.addCourse = function(){
        var y = $http({
            method: 'GET',
            url: '/updateCourse',
            params: {"coursePrefix": $scope.course.prefix, "courseNumber":$scope.course.number, "courseName":$scope.course.name,
                "semester": $scope.course.semester, "pub": $scope.course.public }
        }).then(function (response) {
            debugger;
            $scope.course = response.data;
        }, function errorCallBack(response) {
            alert("add course error\n");
        });
    };

    $scope.enrollCourse = function() {
        var y = $http({
            method: 'GET',
            url: '/enrollCourse',
            params: {"courseCode": $scope.course.code}
        }).then(function (response) {
            debugger;
        }, function errorCallBack(response) {
            alert("add course error\n");
        });
    }
});

