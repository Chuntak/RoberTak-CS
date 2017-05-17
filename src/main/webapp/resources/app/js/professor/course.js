/**
 * Created by Calvin, Chuntak, Rob, Susan on 4/1/2017.
 */
/* FACTORY TO HANDLE HTTP REQUEST LOGIC */
angular.module('homeApp').factory('httpCourseFactory', function($http, global) {
    /* SET SINGLETON LIKE OBJECT */
    var properties = this;

    /* getTag - GETS TAGS FOR TAGGING COURSE IN ADD/EDIT COURSE */
    properties.getTag = function(){
        return $http.get("/getTag");
    }

    /* getCourse - GET LIST OF COURSES PROFESSOR TEACHES */
    properties.getCourse = function(){
        return $http.get('/getCourse');
    }

    /* updateCourse - ADDS/UPDATES A COURSE */
    properties.updateCourse = function(params){
        return $http({
            method: 'GET',
            url: '/updateCourse',
            params: params
        });
    };

    /* updateAssignment - ADDS OR UPDATES AN ASSIGNMENT */
    properties.searchCourse = function (searchModel) {
        return $http.get("/searchCourse", { params : searchModel } );
    };

    /* selectCourse - TELLS THE SERVER WHAT TYPE OF OWNER OF THE SELECTED COURSE IS */
    properties.selectCourse = function (isOwner) {
        return $http.get("/updateIsOwner", { params : { isOwner : isOwner } });
    };

    /* deleteCourse - DELETES A COURSE */
    properties.deleteCourse = function(course){
        return $http({
            method: 'GET',
            url: '/deleteCourse',
            params: {"id": course.id, "code" : course.code}
        });
    };

    return properties;
});

/* COURSE CONTROLLER */
angular.module('homeApp').controller('courseCtrl', function ($scope, $http, $state, $templateCache, global, httpCourseFactory) {

    /******************************************INITALIZING THE MODAL************************************************/
    /* GET THE MODALS */
    var courseModal = document.getElementById('courseModal');
    var searchModal = document.getElementById('searchModal');
    /* GET THE BUTTON THAT OPENS THE MODAL */
    var courseModalAddBtn = document.getElementById("addCourse");
    /* GET THE <span> ELEMENT THAT CLOSES THE MODAL */
    var courseModelCloseBtn = document.getElementById("courseModelCloseBtn");
    var searchModalCloseBtn = document.getElementById("searchModalCloseBtn");
    /* WHEN THE USER CLICKS ON THE BUTTON, OPEN THE MODAL */
    courseModalAddBtn.onclick = function() {
        $scope.getTag();
        $scope.course = {};
        $scope.courseTaggedList = [];
        $scope.$apply();
        courseModal.style.display = "block";
    };
    /* WHEN THE USER CLICKS ON <span> (x), CLOSE THE MODAL */
    courseModelCloseBtn.onclick = function() {
        courseModal.style.display = "none";
    };
    searchModalCloseBtn.onclick = function() {
        searchModal.style.display = "none";
    };
    /* WHEN THE USER CLICKS ANYWHERE OUTSIDE THE MODAL, CLOSE IT */
    window.onclick = function(event) {
        if (event.target === courseModal) {
            courseModal.style.display = "none";
        }
        if (event.target === searchModal) {
            searchModal.style.display = "none";
        }
    };


/***************************************************************************************************************/
    /* INIT VARIABLES */
    $scope.course = {id:0};
    $scope.lastEditedCourse = {};
    $scope.tagList = {};
    $scope.selectedTag = "";
    $scope.courseTaggedList = [];

    /*GETS THE TAGS FROM DATABASE FOR SELECTION*/
    $scope.getTag = function() {
        httpCourseFactory.getTag().success(function (response){
            $scope.tagList = response;
        }).error(function(response){
            console.log(response);
        });
    };

    $scope.getTag();
    /*ADD TAG TO LIST */
    $scope.addTag = function(tag){
        if($scope.courseTaggedList.indexOf($scope.selectedTag) === -1 && $scope.selectedTag !== ""){
            $scope.courseTaggedList.push($scope.selectedTag);
        }
        $scope.selectedTag = "";
    };

    /* DELETE TAG FROM LIST */
    $scope.deleteTag = function(courseTagged) {
        for(var i = 0; i < $scope.courseTaggedList.length; i++){
            if($scope.courseTaggedList[i] === courseTagged){
                $scope.courseTaggedList.splice(i,1);
                break;
            }
        }
    };

    /*ADDS OR EDITS A COURSE */
    $scope.updateCourse = function(){
        var crsid = $scope.course.id;
        /* MAKE PARAMS FOR HTTP REQUEST */
        var params = {
            "id": $scope.course.id,
            "prefix": $scope.course.prefix,
            "number":$scope.course.number,
            "name":$scope.course.name,
            "semester": $scope.course.semester,
            "ano":$scope.course.ano,
            "pub": $scope.course.pub,
            "tagNames" : $scope.courseTaggedList
        };
        /* MAKE HTTP REQUEST VIA FACTORY */
        httpCourseFactory.updateCourse(params).success(function (response) {
            courseModal.style.display = "none";
            if(response.id !== crsid) {  /* ADD TO THE PANE */
                var code = response.code;
                var id = response.id;
                var firstName = response.profFirstName;
                var lastName = response.profLastName;
                $scope.courses.push({
                    "id": id,
                    "prefix": $scope.course.prefix,
                    "number": $scope.course.number,
                    "name": $scope.course.name,
                    "semester": $scope.course.semester,
                    "ano" : $scope.course.ano,
                    "profFirstName": firstName,
                    "profLastName": lastName,
                    "code": code,
                    "pub": $scope.course.pub
                });
                /* DETERMINE IF ADDING NEW COURSE TO SET SELECTED COURSE PROPERLY*/
                if(!$scope.course.id){
                    /* SET SELECTED COURSE TO NEWLY ADDED COURSE */
                    $scope.selectCourse($scope.courses[$scope.courses.length-1], $scope.courses.length-1);
                }
                /* CLEAR THE courseTaggedList SINCE MODAL IS CLOSED */
                $scope.courseTaggedList = [];
            } else { /* EDIT THE PANE */
                $scope.lastEditedCourse.prefix = $scope.course.prefix;
                $scope.lastEditedCourse.number = $scope.course.number;
                $scope.lastEditedCourse.name = $scope.course.name;
                $scope.lastEditedCourse.semester = $scope.course.semester;
                $scope.lastEditedCourse.ano = $scope.course.ano;
                $scope.lastEditedCourse.pub = $scope.course.pub;
                /* CLEAR THE courseTaggedList SINCE THE MODAL IS CLOSED */
                $scope.courseTaggedList = [];
            }
        }).error(function(response){
            console.log(response);
        });
    };

    /*SELECT COURSE*/
    $scope.selected = 0;
    $scope.selectCourse = function(course, index){
        if(course) /*IF COURSE IS NOT NULL THEN ITS TRUE*/ {
            $scope.selected = index;
            global.setCourseId(course.id);
        } else {
            $scope.selected = -1;
            global.setCourseId(-1);
        }

        httpCourseFactory.selectCourse(true).success(function(response){
            var reloadData = function(){
                $templateCache.remove("/announcements");
                $templateCache.remove("/syllabus");
                $templateCache.remove("/assignments");
                $templateCache.remove("/documents");
                $templateCache.remove("/grades");
                $templateCache.remove("/quiz");
                $state.reload();
            };
            reloadData();
            /*ENABLE POSTING IN FORUMS*/
            document.getElementById("accordion").disabled = false;
        }).error(function(response){console.log("select course to server error")});
        /* RELOAD TAB DATA */
    };

    /* GET COURSES FOR PROFESSOR */
    httpCourseFactory.getCourse().success(function(response) {
        var courseList = response;
        $scope.courses = [];
        /* ADD COURSES TO MODEL */
        for(i = 0; i < courseList.length; i++) {
            var course = courseList[i];
            var courseJson = {
                "id": course.id ,
                "prefix":course.prefix,
                "number":course.number,
                "name":course.name,
                "semester":course.semester,
                "ano":course.ano ,
                "profFirstName":course.profFirstName,
                "profLastName":course.profLastName,
                "code":course.code,
                "pub":course.pub
            };
            $scope.courses.push(courseJson);
        }
        /* SET THE CURRENT COURSE ID */
        global.setCourseId($scope.courses[0].id);
    }).error(function(response){
        console.log(response);
    });

    /*EDITS A COURSE*/
    $scope.editCourse = function(course){
        global.setCourseId(course.id);
        $state.reload();
        $scope.lastEditedCourse = course; /*saves when returned we change*/
        $scope.course = {};
        $scope.course.id = course.id;
        $scope.course.prefix = course.prefix;
        $scope.course.number = course.number;
        $scope.course.name = course.name;
        $scope.course.semester = course.semester;
        $scope.course.profFirstName = course.profFirstName;
        $scope.course.profLastName = course.profLastName;
        $scope.course.code = course.code;
        $scope.course.pub = course.pub;
        $scope.course.ano = course.ano;

        $http.get("/getTag", { params: {"taggableId" : global.getCourseId() , "taggableType" : "course"} }).success(function (response){
            $scope.courseTaggedList = response;
            /*DISPLAY THE MODAL*/
            $scope.getTag();
            courseModal.style.display = "block";
        }).error(function(response){
            console.log(response);
        });
    };

    /*DELETES A COURSE*/
    $scope.deleteCourse = function(course, index){
        httpCourseFactory.deleteCourse(course).success(function (response) {
            if(response === true) {  /*add*/
                /* REMOVE DELETED COURSE FROM MODEL */
                $scope.courses.splice(index, 1);
                /*SETS THE SELECTED COURSE TO THE FIRST ONE*/
                /*@TODO fix the blinking selectCourse gets called before this*/
                $scope.selectCourse($scope.courses[0], 0);
            }
        }).error(function(response){
            console.log(respones);
        });
    };


    /************************************************************/
    /**************SEARCH FUNCTIONS ****************************/
    /***********************************************************/


    /*THE SEARCH MODEL THAT WE WILL USE*/
    $scope.search = {};
    $scope.lastSearch = {};
    $scope.search.tagNames = [];
    /*PAGE NUMBER THAT WE WILL USE FOR PAGENATION*/
    $scope.search.pageNum = 0;
    /*THIS IS TEH SEARCH RESULTS FOR COURSES*/
    $scope.searchCourseResults = [];
    /*THIS IS THE SEARCH TAG THAT IS SELECTED*/
    $scope.selectedSearchTag = "";
    /*THIS IS THE FUNCTION THAT WILL SHOW THE SEARCH MODEL*/
    $scope.showSearchModel = function (){
        searchModal.style.display = "block";
    };
    /*THIS IS THE SEARCH COURSE FUNCTION, CALLS HTTP REQUESTS TO SERVER TO SEARCH*/
    $scope.searchCourse = function() {
        $scope.search.pageNum = 0;
        httpCourseFactory.searchCourse($scope.search).success(function(response) {
            /*RESETS THE SEARCH BAR/SEARCH INFO*/
            $scope.lastSearch = $scope.search;
            $scope.search = {};
            $scope.search.tagNames = [];
            $scope.searchCourseResults = response;
            /*DISPLAYS THE PAGE NUMBER*/
            document.getElementById("paginationLabelId").innerHTML = "Page " + ($scope.lastSearch.pageNum + 1);
            searchModal.style.display = "none";
        }).error(function(response) {
            console.log("Search course error:" + response);
        });
    };

    /*THIS IS THE FUNCTION TO ADD SEARCH TAG*/
    $scope.addSearchTag = function () {
        if($scope.search.tagNames.indexOf($scope.selectedSearchTag) === -1 && $scope.selectedSearchTag !== ""){
            $scope.search.tagNames.push($scope.selectedSearchTag);
        }
        $scope.selectedSearchTag = "";
    };

    /*THIS IS THE FUNCTION TO REMOVE SEARCH TAG*/
    $scope.deleteSearchTag = function(searchTagged) {
        for(var i = 0; i < $scope.search.tagNames.length; i++){
            if($scope.search.tagNames[i] === searchTagged){
                $scope.search.tagNames.splice(i,1);
                break;
            }
        }
    };

    $scope.selectedCourseResult = 0;
    $scope.selectCourseResults = function(result, index) {
        if(result) /* IF COURSE IS NOT NULL THEN ITS TRUE */ {
            $scope.selectedCourseResult = index;
            global.setCourseId(result.id);
        } else {
            $scope.selectedCourseResult = -1;
            global.setCourseId(-1);
        }
        httpCourseFactory.selectCourse(false).success(function(response){
            var reloadData = function(){
                $templateCache.remove("/announcements");
                $templateCache.remove("/syllabus");
                $templateCache.remove("/assignments");
                $templateCache.remove("/documents");
                $templateCache.remove("/grades");
                $templateCache.remove("/quiz");
                $state.reload();
            };
            reloadData();
            /*DISABLE POSTING IN FORUMS*/
            document.getElementById("accordion").disabled = true;
        }).error(function(response){console.log("select course to server error")});
        /* RELOAD TAB DATA */
    };

    /*PAGINATION, CHANGES TO EITHER THE NEXT OR PREVIOUS PAGE*/
    $scope.changePageByOne = function(num) {
        /*IF THE PAGE IS NEGATIVE WE DON'T CALL THE REQUEST*/
        if($scope.lastSearch.pageNum + num === -1) return;
        /*IF THE NUMBER OF ELEMENT IS BEING DISPLAYED IS LESS THAN THE NUMBER OF ELEMENTS
            THAT SHOULD BE DISPLAYED THAT MEANS THAT WE REACH THE LAST PAGE, THEREFORE WE WON'T PROCEED TO NEXT PAGE
         */
        if($scope.searchCourseResults.length < 3 && num > 0) return;

        $scope.lastSearch.pageNum += num;
        httpCourseFactory.searchCourse($scope.lastSearch).success(function(response) {
            if(response.length !== 0) {
                /*RESETS THE SEARCH BAR/SEARCH INFO*/
                $scope.searchCourseResults = response;
                /*DISPLAY THE PAGE NUMBER*/
                document.getElementById("paginationLabelId").innerHTML = "Page " + ($scope.lastSearch.pageNum + 1);
            } else {
                $scope.lastSearch.pageNum -= 1;
            }
            debugger;
        }).error(function(response) {
            console.log("Pagination error");
        });
    }
});


