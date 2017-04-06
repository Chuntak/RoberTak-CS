/**
 * Created by rvtru on 4/1/2017.
 */
var homeApp = angular.module("homeApp");
homeApp.config(function($stateProvider, $urlRouterProvider, $locationProvider) {
    // REMOVE HASH SYMBOL
    $locationProvider.html5Mode(true);
    $urlRouterProvider.otherwise("/");
    $stateProvider
        .state("announcements", {
            url: "/",
            templateUrl: '/announcements',
            controller: 'announcementsCtrl',
            controllerAs: 'annCtrl'
        })
        .state('syllabus', {
            //url: "/syllabus",
            templateUrl: '/syllabus',
            controller:  'syllabusCtrl',
            controllerAs:  'syllCtrl'
        })
        .state('assignments', {
            //url: "/assignments",
            templateUrl: '/assignments',
            controller:  'assignmentsCtrl',
            controllerAs:  'assmtCtrl'
        })
        .state('documents', {
            //url: "/documents",
            templateUrl: '/documents',
            controller:  'documentsCtrl',
            controllerAs:  'docsCtrl'
        })
        .state('grades', {
            //url: "/grades",
            templateUrl: '/grades',
            controller:  'gradesCtrl',
            controllerAs:  'gradeCtrl'
        })
    //.otherwise({ redirectTo: '/' });
});

homeApp.controller('tabsCtrl', function($scope, $state) {
    // create a message to display in our view
    $scope.tabs = [
        { state : 'announcements', label : 'Announcements' },
        { state : 'syllabus', label : 'Syllabus' },
        { state : 'assignments', label : 'Assignments' },
        { state : 'documents', label : 'Documents' },
        { state : 'grades', label : 'Grades' }
    ];

    $scope.selectedTab = $scope.tabs[0];
    $scope.setSelectedTab = function(tab) {
        $scope.selectedTab = tab;
        reloadData();
    }

    $scope.tabClass = function(tab) {
        if ($scope.selectedTab == tab) {
            return "active";
        } else {
            return "";
        }
    }
    var reloadData = function(){
        $state.reload();
    }

});

// create the controller and inject Angular's $scope
homeApp.controller('announcementsCtrl', function($scope, $state) {
    // create a message to display in our view
    $scope.message = 'this is the announcements page!';
    var reloadData = function(){
        $state.reload();
    }

});

homeApp.controller('syllabusCtrl', function($scope, $state) {
    // create a message to display in our view
    $scope.message = 'this is the syllabus page!';
    var vm = this;
    // SET NGCLICK WHEN YOU WANT TO RELOAD
    vm.reloadData = function(){
        $state.reload();
    }
});

homeApp.controller('assignmentsCtrl', function($scope, $state) {
    // create a message to display in our view
    $scope.message = 'this is the assignments page!';
    var vm = this;
    // SET NGCLICK WHEN YOU WANT TO RELOAD
    vm.reloadData = function(){
        $state.reload();
    }
});

homeApp.controller('documentsCtrl', function($scope, $state) {
    // create a message to display in our view
    $scope.message = 'this is the content of $scope.message on documents tab!';
    var vm = this;
    // SET NGCLICK WHEN YOU WANT TO RELOAD
    vm.reloadData = function(){
        $state.reload();
    }
});

homeApp.controller('gradesCtrl', function($scope, $state) {
    // create a message to display in our view
    $scope.message = 'this is the grades page!';
    var vm = this;
    // SET NGCLICK WHEN YOU WANT TO RELOAD
    vm.reloadData = function(){
        $state.reload();
    }
});