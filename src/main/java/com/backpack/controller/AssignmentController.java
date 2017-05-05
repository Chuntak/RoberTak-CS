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

    /**
     * uploadAssignment - called when updating an assignment that contains a file
     * @param assignment - assignment to be added to db
     * @param session - current session of user
     * @return AssignmentModel with any new info needed
     */
    @RequestMapping(value="/uploadAssignment", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public @ResponseBody
    AssignmentModel uploadAssignment(@ModelAttribute("assignment") AssignmentModel assignment, HttpSession session) {
        return new AssignmentDAO().uploadAssignment(assignment);
    }

    /**
     * uploadSubmission - called when uploading a student's submission for an assignment
     * @param assignment - submission to be added to db
     * @param session - current session of user
     * @return AssignmentModel with any new info needed
     */
    @RequestMapping(value="/uploadSubmission", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public @ResponseBody
    AssignmentModel uploadSubmission(@ModelAttribute("assignment") AssignmentModel assignment, HttpSession session) {
        return new AssignmentDAO().uploadSubmission(assignment, (int)session.getAttribute("id"));
    }

    /**
     * updateAssignment - called when updating an assignment w/o a file
     * @param assignment - assignment to be added to db
     * @param session - current session of user
     * @return AssignmentModel with any new info needed
     */
    @RequestMapping(value="/updateAssignment", method = RequestMethod.POST)
    public @ResponseBody
    AssignmentModel updateAssignment(@RequestBody AssignmentModel assignment, HttpSession session) {
        return new AssignmentDAO().updateAssignment(assignment);
    }

    @RequestMapping(value="/getAssignments", method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<AssignmentModel> getAssignments(Integer crsId, String gradableType, HttpSession session) {
        return new AssignmentDAO().getAssignments(crsId, gradableType, (int)session.getAttribute("id"));
    }

    @RequestMapping(value="/deleteAssignment", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody
    boolean deleteAssignment(@ModelAttribute("assignment") AssignmentModel assignment, HttpSession session) {
        return new AssignmentDAO().deleteAssignment(assignment);
    }

}
