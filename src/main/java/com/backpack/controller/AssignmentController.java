package com.backpack.controller;

import com.backpack.models.*;
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
public class AssignmentController {
    /*for datastore*/
    BackpackServiceImplementation BackpackService;
    @Autowired
    public AssignmentController(BackpackServiceImplementation BackpackService){
        this.BackpackService = BackpackService;
    }

    /*assignment model*/
    @ModelAttribute("assignment")
    public AssignmentModel getCourseModel(){
        return new AssignmentModel();
    }


    /*returns the right assignment page*/
    @RequestMapping(value="/assignments", method = RequestMethod.GET)
    public String loadAssignments(HttpSession session) {
        if(session.getAttribute("userType").equals("stud")) {
            return "Student/tabs/assignments";
        } else {
            return "Professor/tabs/assignments";
        }
    }


}
