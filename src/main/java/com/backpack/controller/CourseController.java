package com.backpack.controller;

import com.backpack.dao.CourseDAO;
import com.backpack.dao.TagDAO;
import com.backpack.models.CourseModel;
import com.backpack.models.TagModel;
import com.backpack.service.BackpackServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Created by Calvin on 4/3/2017.
 */

@Controller
public class CourseController {
    /*for datastore*/
    BackpackServiceImplementation BackpackService;
    @Autowired
    public CourseController(BackpackServiceImplementation BackpackService){
        this.BackpackService = BackpackService;
    }

    @ModelAttribute("course")
    public CourseModel getAssignmentModel(){
        return new CourseModel();
    }

    /**
     * This is an http get request that will save a course into the database and its tag relationships
     * @param course the course model that gets mapped
     * @param tagNames the list of tags that will be saved as relation to the course
     * @param session the http session that this server is on
     * @return the course model with its generated ID and course code from the database
     */
    @RequestMapping(value="/updateCourse", method = RequestMethod.GET)
    public @ResponseBody CourseModel addCourse(@ModelAttribute("course") CourseModel course,
                                               @RequestParam(value = "tagNames", required = false) ArrayList<String> tagNames, HttpSession session) {
        int profId = (Integer) session.getAttribute("id");
        course.setProfId(profId);
        CourseModel tmp = new CourseDAO().updateCourse(course);
        if(tagNames != null) new TagDAO().updateTaggedCourse(tagNames, tmp.getId());
        return tmp;
    }

    /*gets the course returns the arraylist course can return professor names/email*/
    @RequestMapping(value="/getCourse", method = RequestMethod.GET)
    public @ResponseBody ArrayList<CourseModel> getCourse(HttpSession session) {
        return new CourseDAO().getCourse((Integer)session.getAttribute("id"));
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
