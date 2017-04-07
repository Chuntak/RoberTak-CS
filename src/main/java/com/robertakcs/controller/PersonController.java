package com.robertakcs.controller;

import com.robertakcs.dao.PersonDAO;
import com.robertakcs.models.CourseModel;
import com.robertakcs.models.PersonModel;
import com.robertakcs.service.BackpackServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * Created by Chuntak on 4/1/2017.
 */
@Controller
public class PersonController {
    /*for datastore*/
    BackpackServiceImplementation BackpackService;
    @Autowired
    public PersonController(BackpackServiceImplementation BackpackService){
        this.BackpackService = BackpackService;
    }

    /*person model*/
    @ModelAttribute("person")
    public PersonModel getPersonModel(){
        return new PersonModel();
    }


    /*returns the signin page*/
    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "signIn";
    }

    /*returns the right home page*/
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String loadIndex(@ModelAttribute("person") PersonModel person, BindingResult bindingResult, HttpSession session, ModelMap map){
        person = new PersonDAO().getUserByEmail((String)session.getAttribute("email"));
        session.setAttribute("id", person.getId());
        session.setAttribute("firstName", person.getFirstName());
        session.setAttribute("lastName", person.getLastName());
        session.setAttribute("userType", person.getUserType());
        if(person.getUserType().equals("stud")) {
            return "Student/home";
        } else {
            return "Professor/home";
        }
    }

    /*returns the signup page*/
    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public String signUp(@ModelAttribute("person") PersonModel person, BindingResult bindingResult, ModelMap map){
        return "signUp";
    }

    /*returns to signin page*/
    @RequestMapping(value = "/signOut", method = RequestMethod.GET)
    public String signOut(@ModelAttribute("person") PersonModel person, HttpSession session){
        session.invalidate();
        return "signIn";
    }


    /*registers the person and returns the home page*/
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(@ModelAttribute("person") PersonModel person, BindingResult bindingResult, HttpSession session,ModelMap map){
        //@todo store into database
        int id = new PersonDAO().updateUser(person);
        session.setAttribute("id", id);
        session.setAttribute("firstName", person.getFirstName());
        session.setAttribute("lastName", person.getLastName());
        session.setAttribute("userType", person.getUserType());
        if(person.getUserType().equals("stud")) {
            return "Student/home";
        } else {
            return "Professor/home";
        }
    }


    /*WE CHECK USERS IF THEY ARE NEW/OLD IF NEW WE TRIGGER SIGNUP WHICH TRIGGERS REGISTER WHEN DONE*/
    /*IF OLD, SIMPILY TRIGGERS INDEX*/
    @RequestMapping(value = "/checkUser", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody boolean checkUser(@ModelAttribute("person") PersonModel person, BindingResult bindingResult, HttpSession session, ModelMap map){
        // CHECK IF USER IS IN DB
        session.setAttribute("email", person.getEmail());
        return new PersonDAO().checkUser(person);

    }
}
