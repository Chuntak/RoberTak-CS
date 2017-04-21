/**
 * Created by rvtru on 4/1/2017.
 */

/* GET APP */
var homeApp = angular.module("homeApp");
/* CONFIG UI-ROUTER FOR TAB PANE */
homeApp.config(function($stateProvider, $urlRouterProvider, $locationProvider) {
    /* REMOVE HASH SYMBOL FROM URL THAT IS DEFAULT IN ANGULAR */
    $locationProvider.html5Mode(true);
    /* SET DEFAULT ROUTE */
    $urlRouterProvider.otherwise("/");
    /* BEGIN DEFINING STATES (TAB ROUTES) */
    $stateProvider
        /* NAME THE STATE */
        .state("announcements", {
            /* VISIBLE URL TO USER. MUST BE DIFFERENT THAN SPRING CONTROLLER MAPPING */
            url: "/",
            /* WHERE TO FIND JSP TEMPLATE */
            templateUrl: '/announcements',
            /* SPECIFY ANGULAR CONTROLLER */
            controller: 'announcementsCtrl',
            /* ALTERNATE CONTROLLER DEFINITION */
            controllerAs: 'annCtrl'
        })
        .state('syllabus', {
            templateUrl: '/syllabus',
            controller:  'syllabusCtrl',
            controllerAs:  'syllCtrl'
        })
        .state('assignments', {
            templateUrl: '/assignments',
            controller:  'assignmentsCtrl',
            controllerAs:  'assmtCtrl'
        })
        .state('documents', {
            templateUrl: '/documents',
            controller:  'documentsCtrl',
            controllerAs:  'docsCtrl'
        })
        .state('grades', {
            templateUrl: '/grades',
            controller:  'gradesCtrl',
            controllerAs:  'gradeCtrl'
        })
});

/* BEGIN DEFINING THE CONTROLLERS FOR STATES. INJECT $state */
homeApp.controller('tabsCtrl', function($scope, $state) {
    /* DEFINE TABS FOR NG-REPEAT IN JSP */
    $scope.tabs = [
        { state : 'announcements', label : 'Announcements' },
        { state : 'syllabus', label : 'Syllabus' },
        { state : 'assignments', label : 'Assignments' },
        { state : 'documents', label : 'Documents' },
        { state : 'grades', label : 'Grades' }
    ];
    /* DEFAULT SELECTED TAB */
    $scope.selectedTab = $scope.tabs[0];
    /* SET SELECTED TAB ON CLICK */
    $scope.setSelectedTab = function(tab) {
        $scope.selectedTab = tab;
        /* REFRESH TAB DATA (CONTROLLER REFRESHES)*/
        reloadData();
    };
    /* SET CSS FOR TAB TO SHOW SELECTED TAB */
    $scope.tabClass = function(tab) {
        if ($scope.selectedTab === tab) {
            return "active";
        } else {
            return "";
        }
    };
    /* RELOAD TAB DATA */
    var reloadData = function(){
        $state.reload();
    }

});

// // create the controller and inject Angular's $scope
// homeApp.controller('announcementsCtrl', function($scope, $state) {
//     // create a message to display in our view
//     $scope.message = 'this is the announcements page!';
//     var reloadData = function(){
//         $state.reload();
//     }
//
// });

// homeApp.controller('syllabusCtrl', function($scope, $state) {
//     // create a message to display in our view
//     $scope.message = 'this is the syllabus page!';
//     var vm = this;
//     // SET NGCLICK WHEN YOU WANT TO RELOAD
//     vm.reloadData = function(){
//         $state.reload();
//     }
// });

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