
package com.robertakcs.controller;

import com.robertakcs.dao.TagDAO;
import com.robertakcs.models.CourseModel;
import com.robertakcs.models.PersonModel;
import com.robertakcs.models.TagModel;
import com.robertakcs.service.RoberTakCSServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Created by Chuntak on 4/5/2017.
 */
public class TagController {
    RoberTakCSServiceInterface roberTakCSService;
    @Autowired
    public TagController(RoberTakCSServiceInterface roberTakCSService){
        this.roberTakCSService = roberTakCSService;
    }

    @ModelAttribute("tag")
    public TagModel getTagModel(){
        return new TagModel();
    }



    /*gets the course returns the arraylist course can return professor names/email*/
    @RequestMapping(value="/getTag", method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<TagModel> getCourse(@ModelAttribute("tag") TagModel tag, HttpSession session) {
        return new TagDAO().getTag(tag);
    }

}
