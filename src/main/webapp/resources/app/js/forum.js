/**
 * Created by rvtru on 4/21/2017.
 */
/**
 * Created by rvtru on 4/20/2017.
 */

var app = angular.module('homeApp');

app.controller("forumCtrl", function ($scope, $http, global){
    /*gets the course to display*/

    $scope.posts = [];
    /* keep track of a new post being made */
    $scope.newPost = {"header":"", "content":""};
    $scope.newComment = {"content":""};
    /* watch the factory to receive the posts from db */
    $scope.$watch(function(){
        return global.courseId;
    }, function(newValue, oldValue){
        /* set new value of posts */
        if(newValue !== undefined && newValue != 0 && newValue !== oldValue ){
            $scope.getPosts({"id":0}, newValue);
        }
    });

    $scope.updatePost = function(post){
        $http.get("/updatePost", {
            params : {
                "id": post.id,
                "header": post.header,
                "content": post.content,
                "parentId": post.parentId,
                "crsId": global.getCourseId(),
                "anon":false
            }
        }).then(function(response){
            if(!post.id){
                $scope.posts.unshift({ "id":response.data, "content":post.content, "header":post.header });
                $scope.newPost.header = "";
                $scope.newPost.content="";
            }
        }),(function(response){
        });
    }

    $scope.updateComment = function (parent, comment) {
        debugger;
        $http.get("/updatePost", {
            params : {
                "id": comment.id,
                "content": comment.content,
                "parentId": parent.id,
                "crsId": global.getCourseId(),
                "anon":false
            }
        }).then(function(response){
            if(!comment.id){
                if(!parent.comments){
                    parent.comments=[];
                }
                parent.comments.push({"content":comment.content, "parentId": parent.id, "id": response.data});
                $scope.newComment.content="";
            }
        }),(function(response){
        });
    }
    $scope.getPosts = function(post, crsId){
        $http.get("/getPost", {
            params : {
                "crsId" : crsId,
                "id": post.id
            }
        }).success(function(response){
            if(post.id > 0){
                post.comments = response;
            }
            else{
                $scope.posts = response;
            }
        }).error(function(response){
        });
    }
    $("a").click(function(){
        return false;
    });

    var reloadData = function(){
        $state.reload();
    }
});