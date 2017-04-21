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

    /*add/update a course*/
    @RequestMapping(value="/updatePost", method = RequestMethod.GET)
    public @ResponseBody
    int updatePost(@ModelAttribute("post") PostModel post, HttpSession session) {
        post.setAuthorId((int)session.getAttribute("id"));
        return new PostDAO().updatePost(post);
    }

    /*gets the course returns the arraylist course can return professor names/email*/
    @RequestMapping(value="/getPost", method = RequestMethod.GET)
    public @ResponseBody ArrayList<PostModel> getPost(@ModelAttribute("post") PostModel post, HttpSession session) {
        ArrayList<PostModel> pl = new PostDAO().getPosts(post);
        int userId = (int)session.getAttribute("id");
        for(int i=0; i<pl.size();i++){
            if(pl.get(i).getAuthorId() == userId){
                pl.get(i).setEditable(true);
            }
        }
        return pl;
    }

}
