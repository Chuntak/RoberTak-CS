package com.robertakcs.controller;

import com.robertakcs.models.PersonModel;
import com.robertakcs.service.RoberTakCSServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Created by Chuntak on 4/5/2017.
 */
public class TagController {
    RoberTakCSServiceInterface roberTakCSService;
    @Autowired
    public TagController(RoberTakCSServiceInterface roberTakCSService){
        this.roberTakCSService = roberTakCSService;
    }

    @ModelAttribute("tag")
    public PersonModel getPersonModel(){
        return new PersonModel();
    }
}
