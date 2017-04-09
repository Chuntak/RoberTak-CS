package com.backpack.controller;

import com.backpack.models.*;
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
public class DocumentController {
    /*for datastore*/
    BackpackServiceImplementation BackpackService;
    @Autowired
    public DocumentController(BackpackServiceImplementation BackpackService){
        this.BackpackService = BackpackService;
    }

    /*document model*/
    @ModelAttribute("document")
    public AssignmentModel getCourseModel(){
        return new AssignmentModel();
    }


    /*returns the corrent document page*/
    @RequestMapping(value="/documents", method = RequestMethod.GET)
    public String loadDocuments(HttpSession session) {
        if(session.getAttribute("userType").equals("stud")) {
            return "Student/tabs/documents";
        } else {
            return "Professor/tabs/documents";
        }
    }


}
