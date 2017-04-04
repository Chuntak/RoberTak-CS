/**
 * Created by Chuntak on 4/3/2017.
 */
var app = angular.module('homeInApp', []);
app.controller('homeCtrl', function ($scope, $http) {
    $scope.userFirstName = sessionStorage.getItem("userFirstName");
});