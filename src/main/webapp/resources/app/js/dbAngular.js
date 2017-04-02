/**
 * Created by Chuntak on 3/29/2017.
 */

var app = angular.module('myApp', []);
app.controller('dbCtrl', function ($scope, $http) {
    $scope.dbTable = [];
    $scope.sqlQuery = "";
    $scope.numColumns = 0;
    $scope.requestToDB = function() {
        debugger;
        var y = $http.post("requestToDB", $scope.sqlQuery).then(function(response){
            debugger;
            $scope.dbTable = response.data;
            $scope.countObjectProperties($scope.dbTable[0]);
        }, function(response) {
            debugger;
            alert("Failed");
        });
    };

    $scope.countObjectProperties = function(obj)
    {
        var count = 0;
        for(var i in obj)
            if(obj.hasOwnProperty(i))
                count++;

        $scope.numColumns = count;
    };

});