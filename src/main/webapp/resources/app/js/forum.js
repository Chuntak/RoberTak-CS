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

    /* getUserId - gets user id in order to check edit permissions */
    properties.getUserId = function(){
        return $http.get("/getId");
    }

    /* getPosts - gets posts/comments for current course/post */
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
                "anon": post.anon,
                "authorId" : post.authorId
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

    /* updateLikes - update likes count of post */
    properties.updateLikes = function(postId){
        return $http.get("/updateLikes", {
            params : {
                "postId": postId
            }
        });
    }

    /* deletePost - deletes a post from db */
    properties.deletePost = function(postId, authorId){
        return $http({
            method: 'GET',
            url: '/deletePost',
            params: {
                "id": postId,
                "authorId" : authorId
            }
        });
    }

    /* return */
    return properties;
});

app.controller("forumCtrl", function ($scope, $http, global, httpForumFactory){

    /* POSTS FOR COURSE */
    $scope.posts = [];
    /* KEEP TRACK OF NEW POST BEING MADE */
    $scope.newPost = {"header":"", "content":"", "anon":false};
    /* KEEP TRACK OF NEW COMMENT BEING MADE */
    $scope.newComment = {"content":""};
    /* KEEP TRACK OF USER ID */
    $scope.userId = 0;
    /* KEEP TRACK OF WHICH POST IS SELECTED */
    $scope.selectedPost = null;

    /* WATCH FOR CHANGE IN courseId TO RECEIVE THE POSTS FOR COURSE */
    $scope.$watch(function(){
        return global.courseId;
    }, function(newValue, oldValue){
        /* IF NEW COURSE IS SELECTED GET NEW POSTS */
        if(newValue !== undefined && newValue != 0 && newValue !== oldValue ){
            $scope.getPosts({"id":0}, newValue);
        }
    });

    /* GET USER ID */
    if(global.getUserId() == 0){
        httpForumFactory.getUserId().success(function(response){
            global.setUserId(response);
            $scope.userId = response;
        }).error(function(response){
            console.log(response);
        });
    }

    /* updatePost - CREATE A POST */
    $scope.updatePost = function(post){
        httpForumFactory.updatePost(post).then(function(response){
            if(!post.id){
                $scope.posts.unshift({
                    "id": response.data.id,
                    "firstName": response.data.firstName,
                    "lastName": response.data.lastName,
                    "content" : post.content,
                    "header" : post.header,
                    "commentCount" : 0,
                    "liked" : 0,
                    "likes" : 0,
                    "anon" : post.anon,
                    "dateDisplay" : "Just now",
                    "authorId" : response.data.authorId,
                    "editable" : true
                });
                $scope.newPost.header = "";
                $scope.newPost.content="";
            }
        }),(function(response){
        });
    }

    /* updateComment - CREATE A COMMENT */
    $scope.updateComment = function (parent, comment) {
        httpForumFactory.updateComment(parent, comment).then(function(response){
            if(!comment.id){
                if(!parent.comments){
                    parent.comments=[];
                }
                parent.comments.push({
                    "content":comment.content,
                    "parentId": parent.id,
                    "id": response.data.id,
                    "firstName": response.data.firstName,
                    "lastName": response.data.lastName,
                    "authorId" : response.data.authorId,
                    "editable" : true
                });
                parent.commentCount++;
                $scope.newComment.content="";
            }
        }),(function(response){
        });
    }

    /* updateLike - LIKES/UNLIKES A POST */
    $scope.updateLike = function(post){
        /* CHECK IF LIKING OR UNLIKING */
        if(post.liked > 0){
            post.likes--;
            post.liked = 0;
        }
        else{
            post.likes++;
            post.liked = 1;
        }
        httpForumFactory.updateLikes(post.id).success(function(response){
        }).error(function(response){
            console.log(response);
        });
    }

    /* getPosts - loads posts for course / comments for post */
    $scope.getPosts = function(post, crsId){
        /* RESET SELECTED POST */
        if($scope.selectedPost){
            /* TURN OFF EDITING FOR OLD SELECTED POST */
            $scope.selectedPost.editing = false;
        }
        $scope.selectedPost = post;
        /* TURN OFF EDIT MODE WHEN SELECTING NEW POST */
        $scope.editmode = false;
        if(post.comments){
            return;
        }
        httpForumFactory.getPosts(post, crsId).success(function(response){
            /* MUST LOOP OVER EVERY POST AND EDIT DATA */
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

    /* deletePost - DELETES POST FROM DB */
    $scope.deletePost = function(post, pindex, comment, cindex){
        /* DELETE POST OR COMMENT (IF GIVEN) FROM DB. comment WILL BE UNDEFINED/NULL IF POST */
        var id = comment ? comment.id : post.id;
        var authorId = comment ? comment.authorId : post.authorId;
        httpForumFactory.deletePost(id, authorId).success(function (response) {
            if(response === true) {
                /* REMOVE POST FROM MODEL OR COMMENT FROM POST MODEL */
                comment ? $scope.posts[pindex].comments.splice(cindex, 1) : $scope.posts.splice(pindex,1);
            }
            else{
                /* DELETE FAILED - PERMISSION DENIED */
                console.log("Delete Post Permission Blocked");
            }
        }).error(function(response){
            console.log(response);
        });
    }

    /* editPost - TURN ON EDITING MODE TO CHANGE UI */
    $scope.editPost = function(post){
        /* SET POST TO editing TO CHANGE UI FOR SINGULAR POST */
        post.editing = true;
        /* SET editMode TO TRUE TO CHANGE UI FOR ALL */
        $scope.editMode = true;
    }

    /* saveEdit - SAVE CHANGES IN EDIT MODE / TURN OFF EDIT MODE */
    $scope.saveEdit = function(post, pindex, comment, cindex){
        /* CHANGE POST/COMMENT VALUES IN MODEL IF NOT EMPTY */
        var h;
        var c;
        var toSave = {};
        if(comment){
            c = document.getElementById("c-content-" + pindex + cindex).value;
            comment.content = c!=='' ? c : comment.content;
            comment.editing = false;
            toSave = comment;
        }
        else{
            h = document.getElementById("p-header-" + pindex).value;
            c = document.getElementById("p-content-" + pindex).value;
            post.header = h!=='' ? h : post.header;
            post.content = c!=='' ? c : post.content;
            post.editing = false;
            toSave = post;
        }

        /* TURN OFF EDITING MODE */
        $scope.editMode = false;
        /* SAVE EDIT IN DB */
        httpForumFactory.updatePost(toSave).success(function(response){
        }).error(function(response){
            console.log(response);
        });
    }

    /* cancelEdit - CANCEL CHANGES MADE IN EDIT MODE BY TURNING OFF EDIT MODE */
    $scope.cancelEdit = function(post){
        /* TURN OFF EDITING MODE */
        post.editing = false;
        $scope.editMode = false;
    }

    /* PREVENT DEFAULT BEHAVIOR FROM CLICKING ANCHORS */
    $("a").click(function(){
        return false;
    });

    /* reloadData - RELOADS STATE OF PAGE */
    var reloadData = function(){
        $state.reload();
    }

});