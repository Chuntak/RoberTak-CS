package com.robertakcs.dao;
import com.robertakcs.models.CourseModel;

import java.util.List;
import java.util.Map;

/**
 * Created by Chuntak on 4/3/2017.
 */
public class CourseDAO extends DAOBase {
    //    Query to getCourse

    public String updateCourse(CourseModel cm) {
        String query = "call update_course(?,?,?,?,?,?,?)";
        List l = dbs.getJdbcTemplate().queryForList(query, new Object[]{cm.getId(), cm.getCourseName(),
                cm.getCoursePrefix(), cm.getCourseNumber(), cm.getProfId(), cm.getPub(), cm.getSemester()});
        return (String)((Map)l.get(0)).get("@code");
    }

}
