package com.backpack.controller;

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
        if(session.getAttribute("userType").equals("stud")) {
            return "Student/tabs/syllabus";
        } else {
            return "Professor/tabs/syllabus";
        }
    }

    /*gets the tag returns the arraylist tag */
    @RequestMapping(value="/uploadSyllabus", method = RequestMethod.POST, consumes = {"multipart/form-data"},produces="application/json")
    public @ResponseBody
    String uploadSyllabus(@RequestParam(value = "file") MultipartFile file, @ModelAttribute("syllabus") SyllabusModel syllabus, HttpSession session) {
        return "";
    }
}