package com.backpack.controller;

import com.backpack.dao.AnnouncementDAO;
import com.backpack.models.AnnouncementModel;
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
public class AnnouncementController {
    /*for datastore*/
    BackpackServiceImplementation BackpackService;
    @Autowired
    public AnnouncementController(BackpackServiceImplementation BackpackService){
        this.BackpackService = BackpackService;
    }

    /*model*/
    @ModelAttribute("announcement")
    public AnnouncementModel getAnnouncementModel(){
        return new AnnouncementModel();
    }


    /*returns the announcements page*/
    @RequestMapping(value="/announcements", method = RequestMethod.GET)
    public String loadAnnouncements(HttpSession session) {
        return "tabs/announcements";
    }


    /*update Announcement*/
    @RequestMapping(value="/updateAnnouncement", method = RequestMethod.GET)
    public @ResponseBody
    AnnouncementModel updateAnnouncement(@ModelAttribute("announcement") AnnouncementModel announcement, HttpSession session) {
        AnnouncementModel am = new AnnouncementDAO().updateAnnouncement(announcement);
        return am;
    }

    /*Delete Announcement*/
    @RequestMapping(value="/deleteAnnouncement", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody
    boolean deleteAnnouncement(@ModelAttribute("announcement") AnnouncementModel announcement, HttpSession session) {
        return new AnnouncementDAO().deleteAnnouncement(announcement);
    }

    /*GET Announcement*/
    @RequestMapping(value="/getAnnouncement", method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<AnnouncementModel> getAnnouncement(@ModelAttribute("announcement") AnnouncementModel announcement, HttpSession session) {
        return new AnnouncementDAO().getAnnouncement(announcement);
    }




}