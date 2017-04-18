package com.backpack.controller;

import com.backpack.dao.SyllabusDAO;
import com.backpack.databaseConnection.DBSingleton;
import com.backpack.models.SyllabusModel;
import com.backpack.service.BackpackServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

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

    /*person model*/
    @ModelAttribute("syllabus")
    public SyllabusModel getSyllabusModel(){
        return new SyllabusModel();
    }

    /*returns the syllabus page*/
    @RequestMapping(value="/syllabus", method = RequestMethod.GET)
    public String loadSyllabus(HttpSession session) {
        return "tabs/syllabus";
    }

    @RequestMapping(value="/uploadSyllabus", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public @ResponseBody
    SyllabusModel uploadSyllabus(@ModelAttribute("syllabus") SyllabusModel syllabus, HttpSession session) {
        return new SyllabusDAO().uploadSyllabus(syllabus);
    }

    @RequestMapping(value="/getSyllabus", method = RequestMethod.GET)
    public @ResponseBody SyllabusModel getSyllabus(@ModelAttribute("syllabus") SyllabusModel syllabus, HttpSession session) {
        return new SyllabusDAO().getSyllabus(syllabus);
    }

    @RequestMapping(value="/deleteSyllabus", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody boolean deleteSyllabus(@ModelAttribute("syllabus") SyllabusModel syllabus, HttpSession session) {
        return new SyllabusDAO().deleteSyllabus(syllabus);
    }
}
