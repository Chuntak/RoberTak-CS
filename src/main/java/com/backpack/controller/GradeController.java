package com.backpack.controller;

import com.backpack.dao.CourseDAO;
import com.backpack.dao.GradeDAO;
import com.backpack.models.AssignmentModel;
import com.backpack.models.CourseModel;
import com.backpack.models.GradableModel;
import com.backpack.models.GradeModel;
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

    /*model*/
    @ModelAttribute("gradable")
    public GradableModel getGradableModel(){
        return new GradableModel();
    }

    /*returns the corrent grades page*/
    @RequestMapping(value="/grades", method = RequestMethod.GET)
    public String loadGrades(HttpSession session) {
        return "tabs/grades";
    }

    /*gets the course returns the arraylist course can return professor names/email*/
    @RequestMapping(value="/getGrade", method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<GradeModel> getGrade(@ModelAttribute("grade") GradeModel grade, HttpSession session) {
        return new GradeDAO().getGrade(grade);
    }



    /*********************  GRADABLE  ********************/

    /*returns the current grades page*/
    @RequestMapping(value="/gradable", method = RequestMethod.GET)
    public String loadGradable(HttpSession session) {
        return "tabs/grades";
    }

    /*gets the course returns the arraylist course can return professor names/email*/
    @RequestMapping(value="/getGradable", method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<GradableModel> getGradable(@ModelAttribute("gradable") GradableModel gradable, HttpSession session) {
        return new GradeDAO().getGradable(gradable);
    }

    /*update DOCUMENT*/
    @RequestMapping(value="/updateGradable", method = RequestMethod.GET , produces="application/json")
    public @ResponseBody
    GradableModel updateGradable(@ModelAttribute("gradable") GradableModel gradable, HttpSession session) {
        GradableModel dm = new GradeDAO().updateGradable(gradable);
        return dm;
    }

    /*Delete DOCUMENT*/
    @RequestMapping(value="/deleteGradable", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody
    boolean deleteGradable(@ModelAttribute("gradable") GradableModel gradable, HttpSession session) {
        return new GradeDAO().deleteGradable(gradable);
    }

}
