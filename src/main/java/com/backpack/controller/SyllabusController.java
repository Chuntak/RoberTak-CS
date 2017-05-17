package com.backpack.controller;

import com.backpack.dao.SyllabusDAO;
import com.backpack.databaseConnection.DBSingleton;
import com.backpack.models.SyllabusModel;
import com.backpack.service.BackpackServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Created by Robert on 4/5/2017.
 */

@Controller
public class SyllabusController {
    /*for datastore*/
    BackpackServiceImplementation BackpackService;
    @Autowired
    public SyllabusController(BackpackServiceImplementation BackpackService){
        this.BackpackService = BackpackService;
    }

    /**
     * getSylabusModel - get syllabus
     * @return the syllabus
     */
    @ModelAttribute("syllabus")
    public SyllabusModel getSyllabusModel(){
        return new SyllabusModel();
    }

    /**
     * loadSyllabus - loads the syllabus jsp
     * @param session - current user session
     * @return syllabus jsp tab page
     */
    @RequestMapping(value="/syllabus", method = RequestMethod.GET)
    public String loadSyllabus(HttpSession session) {
        return "tabs/syllabus";
    }

    /**
     * uploadSyllabus - uploads a syllabus file
     * @param syllabus - file to upload
     * @param session - current user session
     * @return syllabus that was uploaded
     */
    @RequestMapping(value="/uploadSyllabus", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public @ResponseBody
    SyllabusModel uploadSyllabus(@ModelAttribute("syllabus") SyllabusModel syllabus, HttpSession session) {
        return new SyllabusDAO().uploadSyllabus(syllabus);
    }

    /**
     * getSyllabus - gets syllabus for a course
     * @param syllabus - contains the course id
     * @param session - current user session
     * @return
     */
    @RequestMapping(value="/getSyllabus", method = RequestMethod.GET)
    public @ResponseBody SyllabusModel getSyllabus(@ModelAttribute("syllabus") SyllabusModel syllabus, HttpSession session) {
        return new SyllabusDAO().getSyllabus(syllabus);
    }

    /**
     * deleteSyllabus - deletes syllabus from db and storage
     * @param syllabus - syllabus to delete
     * @param session - current user session
     * @return boolean if succeeded
     */
    @RequestMapping(value="/deleteSyllabus", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody boolean deleteSyllabus(@ModelAttribute("syllabus") SyllabusModel syllabus, HttpSession session) {
        return new SyllabusDAO().deleteSyllabus(syllabus);
    }
}
