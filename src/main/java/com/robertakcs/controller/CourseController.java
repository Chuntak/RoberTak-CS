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
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping(value="/addCourse", method = RequestMethod.GET, produces="text/plain")
    public @ResponseBody String addCourse(@ModelAttribute("course") CourseModel course,  HttpSession session) {
        int profId = (Integer) session.getAttribute("id");
        course.setProfId(profId);
        String courseCode = new CourseDAO().updateCourse(course);
//        //TODO
        return courseCode;
    }
}
