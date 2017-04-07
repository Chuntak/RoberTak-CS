package com.robertakcs.controller;

import com.robertakcs.dao.CourseDAO;
import com.robertakcs.models.AnnouncementModel;
import com.robertakcs.models.CourseModel;
import com.robertakcs.models.EnrolledModel;
import com.robertakcs.models.PersonModel;
import com.robertakcs.service.BackpackServiceImplementation;
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
public class AnnouncementController {
    /*for datastore*/
    BackpackServiceImplementation BackpackService;
    @Autowired
    public AnnouncementController(BackpackServiceImplementation BackpackService){
        this.BackpackService = BackpackService;
    }

    /*model*/
    @ModelAttribute("announcement")
    public AnnouncementModel getCourseModel(){
        return new AnnouncementModel();
    }


    /*returns the announcements page*/
    @RequestMapping(value="/announcements", method = RequestMethod.GET)
    public String loadAnnouncements(HttpSession session) {
        if(session.getAttribute("userType").equals("stud")) {
            return "Student/tabs/announcements";
        } else {
            return "Professor/tabs/announcements";
        }
    }


}
