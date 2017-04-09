
package com.backpack.controller;

import com.backpack.dao.TagDAO;
import com.backpack.models.TagModel;
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
 * Created by Chuntak on 4/5/2017.
 */
@Controller
public class TagController {
    /*for datastore*/
    BackpackServiceImplementation BackpackService;
    @Autowired
    public TagController(BackpackServiceImplementation BackpackService){
        this.BackpackService = BackpackService;
    }
    /*tag model*/
    @ModelAttribute("tag")
    public TagModel getTagModel(){
        return new TagModel();
    }



    /*gets the tag returns the arraylist tag */
    @RequestMapping(value="/getTag", method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<TagModel> getTag(@ModelAttribute("tag") TagModel tag, HttpSession session) {
        return new TagDAO().getTag(tag);
    }

}
