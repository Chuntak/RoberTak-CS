package com.backpack.controller;

import com.backpack.models.AnnouncementModel;
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
