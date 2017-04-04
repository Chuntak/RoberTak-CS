package com.robertakcs.dao;
import com.robertakcs.models.CourseModel;

import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 4/3/2017.
 */
public class CourseDAO extends DAOBase {
    //    Query to getCourse
//    public boolean getCourse(){
//
//    }
//    Query to makeCourse
//    Query to updateCourse
    public int updateUser(CourseModel cm) {
        String query = "call update_user(?,?,?,?,?,?,?)";
        return 0;
    }
}
