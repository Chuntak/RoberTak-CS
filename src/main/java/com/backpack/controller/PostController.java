package com.backpack.controller;

import com.backpack.dao.CourseDAO;
import com.backpack.dao.PostDAO;
import com.backpack.models.CourseModel;
import com.backpack.models.PostModel;
import com.backpack.service.BackpackServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Created by rvtru on 4/20/2017.
 */

@Controller
public class PostController {
    /*for datastore*/
    BackpackServiceImplementation BackpackService;
    @Autowired
    public PostController(BackpackServiceImplementation BackpackService){
        this.BackpackService = BackpackService;
    }

    /**
     * updatePost - add/edit a post to db
     * @param post - post to add/edit to db
     * @param session - current session of user
     * @return post updated
     */
    @RequestMapping(value="/updatePost", method = RequestMethod.GET)
    public @ResponseBody
    PostModel updatePost(@ModelAttribute("post") PostModel post, HttpSession session) {
        /* CHECK THAT USER ISN'T VISITING PROFESSOR */
        if((!(boolean) session.getAttribute("isOwner")) && ((String)session.getAttribute("userType")).equals("prof"))
            return null;
        /* IF EDITING A POST, CHECK THAT USER IS AUTHOR OF POST */
        if (post.getId() > 0 && (int)session.getAttribute("id")!= post.getAuthorId()){
            return null;
        }
        /* SET AUTHOR ID FOR DB */
        post.setAuthorId((int)session.getAttribute("id"));
        /* UPDATE POST IN DB AND RETURN NEW POST ID */
        int postId = new PostDAO().updatePost(post);
        /* CREATE THE POSTMODEL TO RETURN */
        PostModel ret = new PostModel();
        /* SET THE POST ID */
        ret.setId(postId);
        /* SET THE AUTHOR ID */
        ret.setAuthorId((int)session.getAttribute("id"));
        /* RETURN AUTHOR NAME IF NOT ANON */
        if(!post.isAnon()){
            ret.setFirstName((String)session.getAttribute("firstName"));
            ret.setLastName((String)session.getAttribute("lastName"));
        }
        return ret;
    }

    /**
     * updateLikes - updates the like count of a post
     * @param postId - post that was liked/disliked
     * @param session - current user session
     * @return  number of likes the post has now
     */
    @RequestMapping(value="/updateLikes", method = RequestMethod.GET)
    public @ResponseBody
    int updateLikes(Integer postId, HttpSession session) {
        if ((!(boolean) session.getAttribute("isOwner")) && !((String) session.getAttribute("userType")).equals("stud")){
            return -1;
        }
        return new PostDAO().updateLikes((int)session.getAttribute("id"), postId);
    }

    /*gets the course returns the arraylist course can return professor names/email*/

    /**
     * getPost - gets the posts for a course
     * @param post - has the course id to get posts
     * @param session - current user session
     * @return
     */
    @RequestMapping(value="/getPost", method = RequestMethod.GET)
    public @ResponseBody ArrayList<PostModel> getPost(@ModelAttribute("post") PostModel post, HttpSession session) {
        ArrayList<PostModel> pl = new PostDAO().getPosts(post, (int)session.getAttribute("id"));
        int userId = (int)session.getAttribute("id");
        for(int i=0; i<pl.size();i++){
            if(pl.get(i).getAuthorId() == userId){
                pl.get(i).setEditable(true);
            }
        }
        return pl;
    }

    /**
     * deletePost - deletes a post from the db
     * @param post - the post to be deleted
     * @param session - the session with current user info
     * @return true/false if deleted
     */
    @RequestMapping(value="/deletePost", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody
    boolean deletePost(@ModelAttribute("post") PostModel post, HttpSession session) {
        /* CAN'T DELETE POST IF YOU ARE NOT THE PROF OF COURSE, OR IF YOU ARE STUDENT BUT NOT AUTHOR */
        if (((!(boolean) session.getAttribute("isOwner")) && ((String) session.getAttribute("userType")).equals("prof"))
                || ((int)session.getAttribute("id")!= post.getAuthorId() && ((String) session.getAttribute("userType")).equals("stud"))){
            return false;
        }
        /* SET SESSION USER ID AS AUTHORID FOR SECURITY */
        post.setAuthorId((int)session.getAttribute("id"));
        return new PostDAO().deletePost(post);
    }
}
