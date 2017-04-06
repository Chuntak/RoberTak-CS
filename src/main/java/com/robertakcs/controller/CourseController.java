package com.robertakcs.controller;

import com.robertakcs.dao.CourseDAO;
import com.robertakcs.models.CourseModel;
import com.robertakcs.models.EnrolledModel;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Created by Calvin on 4/3/2017.
 */

@Controller
public class CourseController {
    RoberTakCSServiceInterface RoberTakCSService;
    @Autowired
    public CourseController(RoberTakCSServiceInterface todoListService){
        this.RoberTakCSService = todoListService;
    }

    @ModelAttribute("course")
    public CourseModel getCourseModel(){
        return new CourseModel();
    }

    @RequestMapping(value="/updateCourse", method = RequestMethod.GET)
    public @ResponseBody CourseModel addCourse(@ModelAttribute("course") CourseModel course,  HttpSession session) {
        int profId = (Integer) session.getAttribute("id");
        course.setProfId(profId);
        return new CourseDAO().updateCourse(course);
    }

    /*gets the course returns the arraylist course can return professor names/email*/
    @RequestMapping(value="/getCourse", method = RequestMethod.GET)
    public @ResponseBody ArrayList<CourseModel> getCourse(@ModelAttribute("person") PersonModel person, HttpSession session) {
        return new CourseDAO().getCourse(person);
    }

    /*removes the course*/
    @RequestMapping(value="/deleteCourse", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody boolean deleteCourse(@ModelAttribute("course") CourseModel course, HttpSession session) {
        return new CourseDAO().deleteCourse(course);
    }

    /*enroll in course*/
    @RequestMapping(value="/enrollCourse", method = RequestMethod.GET)
    public @ResponseBody CourseModel enrollCourse(@ModelAttribute("course") CourseModel course, HttpSession session) {
        return new CourseDAO().enrollCourse((Integer)session.getAttribute("id"),course);
    }

}
