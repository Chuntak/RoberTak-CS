package com.backpack.controller;

import com.backpack.dao.AssignmentDAO;
import com.backpack.models.*;
import com.backpack.service.BackpackServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

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
    public AssignmentModel getAssignmentModel(){
        return new AssignmentModel();
    }

    /*assignment model*/
    @ModelAttribute("hwfile")
    public HWFileModel getHWFileModel(){
        return new HWFileModel();
    }


    /*returns the right assignment page*/
    @RequestMapping(value="/assignments", method = RequestMethod.GET)
    public String loadAssignments(HttpSession session) {
        return "tabs/assignments";
    }

    @RequestMapping(value="/updateAssignment", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public @ResponseBody
    AssignmentModel updateAssignment(@ModelAttribute("assignment") AssignmentModel assignment, HttpSession session) {
        return new AssignmentDAO().uploadAssignment(assignment);
    }

    @RequestMapping(value="/getAssignment", method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<AssignmentModel> getAssignments(@ModelAttribute("assignment") AssignmentModel assignment, HttpSession session) {
        return new AssignmentDAO().getAssignments(assignment);
    }

    @RequestMapping(value="/deleteAssignment", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody
    boolean deleteAssignment(@ModelAttribute("assignment") AssignmentModel assignment, HttpSession session) {
        return new AssignmentDAO().deleteAssignment(assignment);
    }

}
