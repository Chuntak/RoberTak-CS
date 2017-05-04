package com.backpack.controller;

import com.backpack.models.GradeModel;
import com.backpack.service.BackpackServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * Created by Robert on 4/5/2017.
 */

@Controller
public class GradeController {
    /*for datastore*/
    BackpackServiceImplementation BackpackService;
    @Autowired
    public GradeController(BackpackServiceImplementation BackpackService){
        this.BackpackService = BackpackService;
    }

    /*model*/
    @ModelAttribute("announcement")
    public GradeModel getAnnouncementModel(){
        return new GradeModel();
    }


    /*returns the corrent grades page*/
    @RequestMapping(value="/grades", method = RequestMethod.GET)
    public String loadGrades(HttpSession session) {
        return "tabs/grades";
    }
}
