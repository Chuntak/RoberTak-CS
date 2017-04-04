package com.robertakcs.controller;

import com.robertakcs.dao.CourseDAO;
import com.robertakcs.models.CourseModel;
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

    @RequestMapping(value="/addCourse", method = RequestMethod.GET)
    public String addCourse(@ModelAttribute("course") CourseModel course, BindingResult bindingResult, HttpSession session, ModelMap map) {
        int id = new CourseDAO().updateUser(course);
        session.setAttribute("courseId", id);
        //TODO
        return "Home/signIn";
    }
}
