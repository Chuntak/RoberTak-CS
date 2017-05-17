package com.backpack.controller;

import com.backpack.dao.CourseDAO;
import com.backpack.dao.TagDAO;
import com.backpack.models.CourseModel;
import com.backpack.models.PersonModel;
import com.backpack.models.ProblemModel;
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
    public CourseModel getCourseModel(){
        return new CourseModel();
    }

    /**
     * updateIsOwner - check if owner
     * @param isOwner - boolean value
     * @param session - current session of user
     * @return true or false if owner
     */
    @RequestMapping(value="/updateIsOwner", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody boolean updateIsOwner(@RequestParam(value = "isOwner") boolean isOwner, HttpSession session){
        session.setAttribute("isOwner", isOwner);
        return isOwner;
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

    /**
     * getCourse - get the course fields
     * @param session - current session of user
     * @return an arraylist of all courses fields
     */
    @RequestMapping(value="/getCourse", method = RequestMethod.GET)
    public @ResponseBody ArrayList<CourseModel> getCourse(HttpSession session) {
        return new CourseDAO().getCourse((Integer)session.getAttribute("id"));
    }

    /**
     * deleteCourse - deletes a selected course
     * @param course - course to be deleted
     * @param session - current session of user
     * @return number of rows affected
     */
    @RequestMapping(value="/deleteCourse", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody boolean deleteCourse(@ModelAttribute("course") CourseModel course, HttpSession session) {
        return new CourseDAO().deleteCourse(course);
    }


    /**
     * enrollCourse - enroll to a course
     * @param course - courseModel with getters and setters
     * @param session - current session of user
     * @return the courseModel with fields
     */
    @RequestMapping(value="/enrollCourse", method = RequestMethod.GET)
    public @ResponseBody CourseModel enrollCourse(@ModelAttribute("course") CourseModel course, HttpSession session) {
        return new CourseDAO().enrollCourse((Integer)session.getAttribute("id"),course);
    }


    /**
     * searchCourse - user can search a specific course by any fields thats within the model
     * @param course - courseModel with getters and setters
     * @param tagNames - arraylist of all tags
     * @param session - current session of user
     * @return the search result in arraylist
     */
    @RequestMapping(value="/searchCourse", method = RequestMethod.GET)
    public @ResponseBody ArrayList<CourseModel> searchCourse(@ModelAttribute("course") CourseModel course,
                                 @RequestParam(value = "tagNames", required = false) ArrayList<String> tagNames,HttpSession session) {
        return new CourseDAO().searchCourse(course, tagNames, (Integer)session.getAttribute("id"));
    }

}
