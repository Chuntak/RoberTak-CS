package com.backpack.controller;

import com.backpack.dao.DocumentDAO;
import com.backpack.dao.SyllabusDAO;
import com.backpack.models.*;
import com.backpack.service.BackpackServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
        return "tabs/documents";
    }

    /*Upload DOCUMENT*/
    @RequestMapping(value="/uploadDocument", method = RequestMethod.POST, consumes = {"multipart/form-data"}, produces="text/plain")
    public @ResponseBody
    DocumentModel uploadDocument(@ModelAttribute("document") DocumentModel document, HttpSession session) {
        return new DocumentDAO().uploadDocument(document);
    }

    /*update DOCUMENT*/
    @RequestMapping(value="/updateDocument", method = RequestMethod.POST, consumes = {"multipart/form-data"}, produces="text/plain")
    public @ResponseBody
    boolean updateDocument(@ModelAttribute("document") DocumentModel document, HttpSession session) {
        DocumentModel dm = new DocumentDAO().updateDocument(document);
        return dm != null;
    }
//
//    /*Delete DOCUMENT*/
//    @RequestMapping(value="/deleteDocument", method = RequestMethod.POST, consumes = {"multipart/form-data"}, produces="text/plain")
//    public @ResponseBody
//    boolean deleteDocument(@ModelAttribute("document") DocumentModel document, HttpSession session) {
//        return new DocumentDAO().;
//    }

}
