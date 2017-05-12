/**
 * Created by rvtru on 4/21/2017.
 */
/**
 * Created by rvtru on 4/20/2017.
 */

var app = angular.module('homeApp');

/* FACTORY TO HANDLE HTTP REQUEST LOGIC */
app.factory('httpForumFactory', function($http, global) {
    /* SET SINGLETON LIKE OBJECT */
    var properties = this;

    properties.getPosts = function(post, crsId){
        return $http.get("/getPost", {
                    params : {
                        "crsId" : crsId,
                        "id": post.id
                    }
                });
    }

    /* updatePost - adds or updates an post */
    properties.updatePost = function(post) {
        return $http.get("/updatePost", {
                    params : {
                        "id": post.id,
                        "header": post.header,
                        "content": post.content,
                        "parentId": post.parentId,
                        "crsId": global.getCourseId(),
                        "anon":false
                    }
                });
    }

    /* updateComment - adds or updates a comment to a post */
    properties.updateComment = function(parent, comment){
        return $http.get("/updatePost", {
                    params : {
                        "id": comment.id,
                        "content": comment.content,
                        "parentId": parent.id,
                        "crsId": global.getCourseId(),
                        "anon":false
                    }
                });
    }

    /* updateLikes - updates likes count of post */
    properties.updateLikes = function(postId){
        return $http.get("/updateLikes", {
            params : {
                "postId": postId
            }
        });
    }

    return properties;
});


app.controller("forumCtrl", function ($scope, $http, global, httpForumFactory){
    /*gets the course to display*/

    $scope.posts = [];
    /* keep track of a new post being made */
    $scope.newPost = {"header":"", "content":""};
    $scope.newComment = {"content":""};
    $scope.courseId = 0;

    /* watch the factory to receive the posts from db */
    $scope.$watch(function(){
        return global.courseId;
    }, function(newValue, oldValue){
        /* set new value of posts */
        if(newValue !== undefined && newValue != 0 && newValue !== oldValue ){
            $scope.getPosts({"id":0}, newValue);
            $scope.courseId = newValue;
        }
    });

    $scope.updatePost = function(post){
        httpForumFactory.updatePost(post).then(function(response){
            if(!post.id){
                $scope.posts.unshift({ "id":response.data, "content":post.content, "header":post.header });
                $scope.newPost.header = "";
                $scope.newPost.content="";
            }
        }),(function(response){
        });
    }

    $scope.updateComment = function (parent, comment) {
        httpForumFactory.updateComment(parent, comment).then(function(response){
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

    $scope.updateLike = function(post){
        if(post.liked > 0){
            post.likes--;
            post.liked = 0;
        }
        else{
            post.likes++;
            post.liked = 1;
        }
        httpForumFactory.updateLikes(post.id).success(function(response){
            debugger;
        }).error(function(response){
            console.log(response);
        });
    }

    $scope.getPosts = function(post, crsId){
        httpForumFactory.getPosts(post, crsId).success(function(response){
            $.each(response, function() {
                /* FORMAT TIME & DISPLAY TIME */
                var d = new Date(this.dateCreated);
                this.dateCreated = d.toLocaleDateString("en-US");
                this.dateDisplay = jQuery.timeago(d.toISOString());
            });
            /* CHECK IF GETTING POSTS OR COMMENTS */
            if(post.id > 0){
                post.comments = response;
            }
            else{
                $scope.posts = response;
            }
        }).error(function(response){
            console.log(response);
        });
    }

    $("a").click(function(){
        return false;
    });

    var reloadData = function(){
        $state.reload();
    }

});