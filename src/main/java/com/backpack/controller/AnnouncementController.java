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

    /**
     * getAnnouncementModel - java class with getters and setters of fields
     * @return announcement model
     */
    @ModelAttribute("announcement")
    public AnnouncementModel getAnnouncementModel(){
        return new AnnouncementModel();
    }


    /**
     * loadAnnouncements - loads the tab for announcement
     * @param session - current session of user
     * @return the announcements page
     */
    @RequestMapping(value="/announcements", method = RequestMethod.GET)
    public String loadAnnouncements(HttpSession session) {
        return "tabs/announcements";
    }


    /**
     * updateAnnouncement - adds/edit announcement
     * @param announcement - contains fields needed for an announcement
     * @param session - current session of user
     * @return the model
     */
    @RequestMapping(value="/updateAnnouncement", method = RequestMethod.GET)
    public @ResponseBody
    AnnouncementModel updateAnnouncement(@ModelAttribute("announcement") AnnouncementModel announcement, HttpSession session) {
        AnnouncementModel am = new AnnouncementDAO().updateAnnouncement(announcement);
        return am;
    }

    /**
     * deleteAnnoucement - deletes a selected announcement
     * @param announcement - selected announcement ot be deleted
     * @param session - current session of user
     * @return number of rows affected
     */
    @RequestMapping(value="/deleteAnnouncement", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody
    boolean deleteAnnouncement(@ModelAttribute("announcement") AnnouncementModel announcement, HttpSession session) {
        return new AnnouncementDAO().deleteAnnouncement(announcement);
    }

    /*GET Announcement*/

    /**
     * getAnnouncement - gets the fields for all announcements
     * @param announcement - courseId selected
     * @param session - current session of user
     * @return an arraylist of all fields for announcements
     */
    @RequestMapping(value="/getAnnouncement", method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<AnnouncementModel> getAnnouncement(@ModelAttribute("announcement") AnnouncementModel announcement, HttpSession session) {
        return new AnnouncementDAO().getAnnouncement(announcement);
    }
}