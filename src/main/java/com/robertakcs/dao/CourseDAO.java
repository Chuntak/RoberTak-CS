package com.robertakcs.dao;
import com.robertakcs.models.CourseModel;
import com.robertakcs.models.EnrolledModel;
import com.robertakcs.models.PersonModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    public ArrayList<CourseModel> getCourse(PersonModel pm) {
        String query = "call get_course(?)";
        ArrayList<CourseModel> cml = dbs.getJdbcTemplate().query(query, new Object[]{pm.getId()}, new CourseModelExtractor());
        return cml;
    }

    public boolean deleteCourse(CourseModel cm) {
        String query = "call delete_course(?)";
        return dbs.getJdbcTemplate().update(query, cm.getId()) == 1; /*check if 1 row affected*/
    }

    public boolean enrollCourse(int studId,CourseModel cm) {
        String query = "call update_enrolled(?, ?)";
        return dbs.getJdbcTemplate().update(query, studId, cm.getCourseCode()) == 1; /*check if 1 row affected*/
    }


    private static class CourseModelExtractor implements ResultSetExtractor<ArrayList<CourseModel>> {
        @Override
        public ArrayList<CourseModel> extractData(ResultSet rs) throws SQLException, DataAccessException {
            ArrayList<CourseModel> cml = new ArrayList<CourseModel>();
            while(rs.next()){
                CourseModel cm = new CourseModel();
                if(rs.getObject("id") != null) cm.setId(rs.getInt("id"));
                if(rs.getObject("crsName") != null) cm.setCourseName(rs.getString("crsName"));
                if(rs.getObject("crsPrefix") != null) cm.setCoursePrefix(rs.getString("crsPrefix"));
                if(rs.getObject("crsNum") != null) cm.setCourseNumber(rs.getString("crsNum"));
                if(rs.getObject("crsCode") != null) cm.setCourseCode(rs.getString("crsCode"));
                if(rs.getObject("profId") != null) cm.setProfId(rs.getInt("profId"));
                if(rs.getObject("public") != null) cm.setPub(rs.getBoolean("public"));
                if(rs.getObject("semester") != null) cm.setSemester(rs.getString("semester"));
                if(rs.getObject("firstName") != null) cm.setProfFirstName(rs.getString("firstName"));
                if(rs.getObject("lastName") != null) cm.setProfLastName(rs.getString("lastName"));
                if(rs.getObject("email") != null) cm.setProfEmail(rs.getString("email"));
                cml.add(cm);
            }
            return cml;
        }
    }

}
