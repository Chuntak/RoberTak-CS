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
import java.util.ArrayList;

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
    public DocumentModel getDocumentModel(){
        return new DocumentModel();
    }


    /**
     * uploadDocument - gets all fields and information load to the right tab
     * @param session - current session of user
     * @return the document tab
     */
    @RequestMapping(value="/documents", method = RequestMethod.GET)
    public String loadDocuments(HttpSession session) {
        return "tabs/documents";
    }

    /**
     * uploadDocument - called when updating an document that contains a file
     * @param document - document to be added to db
     * @param session - current session of user
     * @return DocumentModel with any new info needed
     */
    @RequestMapping(value="/uploadDocument", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public @ResponseBody
    DocumentModel uploadDocument(@ModelAttribute("document") DocumentModel document, HttpSession session) {
        return new DocumentDAO().uploadDocument(document);
    }

    /**
     * updateDocument - adds or edit a document
     * @param document - document model fields
     * @param session - current session of user
     * @return document model with all fields updates and added
     */
    @RequestMapping(value="/updateDocument", method = RequestMethod.GET , produces="application/json")
    public @ResponseBody
    boolean updateDocument(@ModelAttribute("document") DocumentModel document, HttpSession session) {
        DocumentModel dm = new DocumentDAO().updateDocument(document);
        return dm != null;
    }

    /**
     * deleteDocument - delete a specific document
     * @param document - selected document to be deleted
     * @param session - current session of user
     * @return the number of rows affected
     */
    @RequestMapping(value="/deleteDocument", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody
    boolean deleteDocument(@ModelAttribute("document") DocumentModel document, HttpSession session) {
        return new DocumentDAO().deleteDocument(document);
    }

    /**
     * getDocument - gets the fields of all documents
     * @param document document model to obtain fields for document
     * @param session - current session of user
     * @return an arraylist of all docuemnts with fields
     */
    @RequestMapping(value="/getDocument", method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<DocumentModel> getDocument(@ModelAttribute("document") DocumentModel document, HttpSession session) {
        return new DocumentDAO().getDocument(document);
    }

}
