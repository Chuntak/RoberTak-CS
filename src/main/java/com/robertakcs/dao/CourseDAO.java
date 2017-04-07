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

    /*calls to the database to update course*/
    public CourseModel updateCourse(CourseModel cm) {
        String query = "call update_course(?,?,?,?,?,?,?)";
        ArrayList<CourseModel> cml = dbs.getJdbcTemplate().query(query, new Object[]{cm.getId(), cm.getName(),
                cm.getPrefix(), cm.getNumber(), cm.getProfId(), cm.getPub(), cm.getSemester()}, new CourseModelExtractor());
        return cml.size() > 0 ? cml.get(0) : null;
    }

    /*calls to the database and retrieve course the id can be a student and professor*/
    public ArrayList<CourseModel> getCourse(int id) {
        String query = "call get_course(?)";
        ArrayList<CourseModel> cml = dbs.getJdbcTemplate().query(query, new Object[]{id}, new CourseModelExtractor());
        return cml;
    }

    /*calls to the database to delete course*/
    public boolean deleteCourse(CourseModel cm) {
        String query = "call delete_course(?)";
        return dbs.getJdbcTemplate().update(query, cm.getId()) == 1; /*check if 1 row affected*/
    }

    /*calls to the database to enroll course*/
    public CourseModel enrollCourse(int studId,CourseModel cm) {
        String query = "call update_enrolled(?, ?)";
        ArrayList<CourseModel> cml = dbs.getJdbcTemplate().query(query, new Object[] {studId, cm.getCode()}, new CourseModelExtractor());
        return cml.size() > 0 ? cml.get(0) : null;/*check if 1 row affected*/
    }

    /*private class to retrieve a list of coursemodel from the resultset returned from the database*/
    private static class CourseModelExtractor implements ResultSetExtractor<ArrayList<CourseModel>> {
        @Override
        public ArrayList<CourseModel> extractData(ResultSet rs) throws SQLException, DataAccessException {
            ArrayList<CourseModel> cml = new ArrayList<CourseModel>();
            if(rs.getMetaData().getColumnCount() > 0) {
                while (rs.next()) {
                    CourseModel cm = new CourseModel();
                    if (columnExists(rs, "id")) cm.setId(rs.getInt("id"));
                    if (columnExists(rs, "crsName")) cm.setName(rs.getString("crsName"));
                    if (columnExists(rs, "crsPrefix")) cm.setPrefix(rs.getString("crsPrefix"));
                    if (columnExists(rs, "crsNum")) cm.setNumber(rs.getString("crsNum"));
                    if (columnExists(rs, "crsCode")) cm.setCode(rs.getString("crsCode"));
                    if (columnExists(rs, "profId")) cm.setProfId(rs.getInt("profId"));
                    if (columnExists(rs, "public")) cm.setPub(rs.getBoolean("public"));
                    if (columnExists(rs, "semester")) cm.setSemester(rs.getString("semester"));
                    if (columnExists(rs, "firstName")) cm.setProfFirstName(rs.getString("firstName"));
                    if (columnExists(rs, "lastName")) cm.setProfLastName(rs.getString("lastName"));
                    if (columnExists(rs, "email")) cm.setProfEmail(rs.getString("email"));
                    cml.add(cm);
                }
            }
            return cml;
        }

    }

}
