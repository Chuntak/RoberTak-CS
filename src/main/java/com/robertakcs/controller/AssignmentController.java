package com.robertakcs.controller;

import com.robertakcs.dao.CourseDAO;
import com.robertakcs.models.*;
import com.robertakcs.service.RoberTakCSServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Created by Robert on 4/5/2017.
 */

@Controller
public class AssignmentController {
    RoberTakCSServiceInterface RoberTakCSService;
    @Autowired
    public AssignmentController(RoberTakCSServiceInterface robertakcsService){
        this.RoberTakCSService = robertakcsService;
    }

    @ModelAttribute("assignment")
    public AssignmentModel getCourseModel(){
        return new AssignmentModel();
    }



    @RequestMapping(value="/assignments", method = RequestMethod.GET)
    public String loadAssignments(HttpSession session) {
        if(session.getAttribute("userType").equals("stud")) {
            return "Student/tabs/assignments";
        } else {
            return "Professor/tabs/assignments";
        }
    }


}
