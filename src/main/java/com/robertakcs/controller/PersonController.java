package com.robertakcs.controller;

import com.robertakcs.dao.PersonDAO;
import com.robertakcs.models.PersonModel;
import com.robertakcs.service.RoberTakCSServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by Chuntak on 4/1/2017.
 */
@Controller
public class PersonController {
    RoberTakCSServiceInterface RoberTakCSService;
    @Autowired
    public PersonController(RoberTakCSServiceInterface todoListService){
        this.RoberTakCSService = todoListService;
    }


    @ModelAttribute("person")
    public PersonModel getPersonModel(){
        return new PersonModel();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "Home/signIn";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String loadIndex(@ModelAttribute("person") PersonModel person, BindingResult bindingResult, ModelMap map){
        return "Home/home";
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public String signUp(@ModelAttribute("person") PersonModel person, BindingResult bindingResult, ModelMap map){
        return "Home/signUp";
    }



    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(@ModelAttribute("person") PersonModel person, BindingResult bindingResult, HttpSession session,ModelMap map){
        //@todo store into database
        int id = new PersonDAO().updateUser(person);
        session.setAttribute("id", id);
        return "Home/index";
    }


    /*WE CHECK USERS IF THEY ARE NEW/OLD IF NEW WE TRIGGER SIGNUP WHICH TRIGGERS REGISTER WHEN DONE*/
    /*IF OLD, SIMPILY TRIGGERS INDEX*/
    @RequestMapping(value = "/checkUser", method = RequestMethod.GET, produces="text/plain")
    public @ResponseBody String checkUser(@ModelAttribute("person") PersonModel person, BindingResult bindingResult, HttpSession session, ModelMap map){
        // CHECK IF USER IS IN DB
        session.setAttribute("email", person.getEmail());
        if(new PersonDAO().checkUser(person)) {
            return "true";
        }
        else {
            return "false";
        }
        // IF USER IS IN DB

    }

    /*
    Signs user out by redirecting to sign in page and removes session & session storage data
     */
    @RequestMapping(value ="/signOut", method = RequestMethod.GET)
    public String signOutUser(@ModelAttribute("person") PersonModel person, BindingResult bindingResult, HttpSession session, ModelMap map){
        session.invalidate();
        return "Home/signIn";
    }

}
