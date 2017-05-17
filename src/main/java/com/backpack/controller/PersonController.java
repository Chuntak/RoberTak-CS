package com.backpack.controller;

import com.backpack.dao.PersonDAO;
import com.backpack.databaseConnection.DBSingleton;
import com.backpack.models.CourseModel;
import com.backpack.models.PersonModel;
import com.backpack.service.BackpackServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

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

    /**
     * getPersonModel - maps 'person' to a PersonModel
     * @return PersonModel
     */
    @ModelAttribute("person")
    public PersonModel getPersonModel(){
        return new PersonModel();
    }

    /**
     * index - returns signIn.jsp
     * @return signIn.jsp
     */
    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "signIn";
    }

    /**
     * loadIndex - loads user home page and sets session data
     * @param person - user logging in
     * @param bindingResult
     * @param session - current session
     * @param map - map of model data
     * @return home.jsp
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String loadIndex(@ModelAttribute("person") PersonModel person, BindingResult bindingResult, HttpSession session, ModelMap map){
        person = new PersonDAO().getUserByEmail((String)session.getAttribute("email"));
        session.setAttribute("id", person.getId());
        session.setAttribute("firstName", person.getFirstName());
        session.setAttribute("lastName", person.getLastName());
        session.setAttribute("userType", person.getUserType());
        session.setAttribute("isOwner", person.getUserType().equals("prof"));
        return "home";
    }

    /**
     * signUp - returns register page
     * @param person - user signing up
     * @param bindingResult
     * @param map
     * @return signUp.jsp
     */
    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public String signUp(@ModelAttribute("person") PersonModel person, BindingResult bindingResult, ModelMap map){
        return "signUp";
    }

    /**
     * signOut - signs user out of app and invalidates session
     * @param person - person signing out
     * @param session - session to invalidate
     * @return the signIn jsp
     */
    @RequestMapping(value = "/signOut", method = RequestMethod.GET)
    public String signOut(@ModelAttribute("person") PersonModel person, HttpSession session){
        session.invalidate();
        return "signIn";
    }


    /**
     * register - registers the user in db and sets session
     * @param person - person to register
     * @param bindingResult
     * @param session - current user session
     * @param map
     * @return home jsp
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(@ModelAttribute("person") PersonModel person, BindingResult bindingResult, HttpSession session,ModelMap map){
        //@todo store into database
        int id = new PersonDAO().updateUser(person);
        session.setAttribute("id", id);
        session.setAttribute("firstName", person.getFirstName());
        session.setAttribute("lastName", person.getLastName());
        session.setAttribute("userType", person.getUserType());
        session.setAttribute("isOwner", person.getUserType().equals("prof"));
        return "home";
    }


    /*WE CHECK USERS IF THEY ARE NEW/OLD IF NEW WE TRIGGER SIGNUP WHICH TRIGGERS REGISTER WHEN DONE*/
    /*IF OLD, SIMPILY TRIGGERS INDEX*/
    @RequestMapping(value = "/checkUser", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody boolean checkUser(@ModelAttribute("person") PersonModel person, BindingResult bindingResult, HttpSession session, ModelMap map){
        // CHECK IF USER IS IN DB
        session.setAttribute("email", person.getEmail());
        return new PersonDAO().checkUser(person);

    }

    /**
     * getEnrolled - get enrooled students in course
     * @param course - course to get roster for
     * @param session - current user session
     * @return boolean if user exists in db already
     */
    @RequestMapping(value="/getEnrolled", method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<PersonModel> getCourse(@ModelAttribute("course") CourseModel course, HttpSession session) {
        return new PersonDAO().getEnrolled(course.getId());
    }

    /**
     * getUserId - get the user id from the session
     * @param session - current user session
     * @return - user id
     */
    @RequestMapping(value = "/getId", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody int getUserId(HttpSession session){
        // RETURN USER ID
        return (int)session.getAttribute("id");

    }
}
