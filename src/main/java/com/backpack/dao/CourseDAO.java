package com.backpack.dao;
import com.backpack.models.CourseModel;
import com.backpack.models.PersonModel;
import com.backpack.models.TagModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Chuntak on 4/3/2017.
 */
public class CourseDAO extends DAOBase {

    /**
     * Adds or edit and existing course in the database
     * if the course id is 0 we add a new course to the database
     * if the course id is greater than 0 we update the existing course
     * @param cm course model containing all the attributes of the course
     * @return a course model containing its id generated from the database
     */
    public CourseModel updateCourse(CourseModel cm) {
        String query = "call update_course(?,?,?,?,?,?,?,?)";
        ArrayList<CourseModel> cml = dbs.getJdbcTemplate().query(query, new Object[]{cm.getId(), cm.getName(),
                cm.getPrefix(), cm.getNumber(), cm.getProfId(), cm.getPub(), cm.getSemester(), cm.getAno()}, new CourseModelExtractor());
        return cml.size() > 0 ? cml.get(0) : cm;
    }

    /**
     * Gets a list of courses that the person is related to
     * @param id identifier for that suspecific person
     * @return a list of courses that the person is related to
     */
    public ArrayList<CourseModel> getCourse(int id) {
        String query = "call get_course(?)";
        ArrayList<CourseModel> cml = dbs.getJdbcTemplate().query(query, new Object[]{id}, new CourseModelExtractor());
        return cml;
    }


    /**
     * Deletes a course from the database given the course model
     * which has the course's id
     * @param cm course model that contains a course id
     * @return true if delete was a success
     */
    public boolean deleteCourse(CourseModel cm) {
        String query = "call delete_course(?)";
        return dbs.getJdbcTemplate().update(query, cm.getId()) == 1; /*check if 1 row affected*/
    }

    /**
     * Enrolls a student identified by studId into a course, given
     * by the course model cm which has its course code that identifies the course
     * @param studId an identifier taht identifies a student
     * @param cm a course model that contains the course code
     *           of the course that the student is enrolling into
     * @return a course model of the course that the student was just enrolled into
     */
    public CourseModel enrollCourse(int studId,CourseModel cm) {
        String query = "call update_enrolled(?, ?)";
        ArrayList<CourseModel> cml = dbs.getJdbcTemplate().query(query, new Object[] {studId, cm.getCode()}, new CourseModelExtractor());
        return cml.size() > 0 ? cml.get(0) : null;/*check if 1 row affected*/
    }

    /**
     * This method calls the search_course procedure from the database, it searches the course based on the given parameters
     * Returns the page of results with the given page number
     * @param cm the model that the search will look for
     * @param tags the tags that will be used to filter the search
     * @return the list of results
     */
    public ArrayList<CourseModel> searchCourse(CourseModel cm, ArrayList<String> tags, int searcherId) {
        boolean hasTags = false;
        if(tags != null) { hasTags = true; }
        String query = "call search_course(?,?,?,?,?,?,?,?,?,?,?)";
        int startIndex = cm.getPageNum() * (CourseModel.PAGESIZE);
        int endIndex = (CourseModel.PAGESIZE);
        ArrayList<CourseModel> cml;

        if(hasTags) {
            HashMap<Integer, CourseModel> cmh = dbs.getJdbcTemplate().query(query, new Object[] {cm.getSchool(), cm.getProfName(),
                    cm.getNumber(), cm.getPrefix(), cm.getName(), cm.getSemester(), cm.getAno(), startIndex, endIndex, searcherId, hasTags} , new CourseModelHashExtractor());
            query = "call search_course_tag_relation(?,?,?,?,?,?,?,?,?,?,?)";
            HashMap<Integer, ArrayList<String>> tmh = dbs.getJdbcTemplate().query(query, new Object[] {cm.getSchool(), cm.getProfName(),
                    cm.getNumber(), cm.getPrefix(), cm.getName(), cm.getSemester(), cm.getAno(), startIndex, endIndex, searcherId, hasTags} , TagDAO.getTagRelationHashExtractor());
            TreeSet tagTreeSet = new TreeSet(tags);
            cml = new ArrayList<CourseModel>();
            for (Map.Entry<Integer, ArrayList<String>> tm : tmh.entrySet()) {
                TreeSet tts = new TreeSet(tm.getValue());
                if (tts.containsAll(tagTreeSet)) {
                    cml.add(cmh.get(tm.getKey()));
                }
            }
        } else {
            cml = dbs.getJdbcTemplate().query(query, new Object[] {cm.getSchool(), cm.getProfName(),
                    cm.getNumber(), cm.getPrefix(), cm.getName(), cm.getSemester(), cm.getAno(), startIndex, endIndex, searcherId, hasTags} , new CourseModelExtractor());
        }

        return cml;
    }
    /*private class to retrieve a list of coursemodel from the resultset returned from the database*/
    private static class CourseModelExtractor implements ResultSetExtractor<ArrayList<CourseModel>> {
        @Override
        public ArrayList<CourseModel> extractData(ResultSet rs) throws SQLException, DataAccessException {
            ArrayList<CourseModel> cml = new ArrayList<CourseModel>();
            if(rs != null && rs.getMetaData().getColumnCount() > 0) {
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
                    if (columnExists(rs, "ano")) cm.setAno(rs.getString("ano"));
                    if (columnExists(rs, "firstName")) cm.setProfFirstName(rs.getString("firstName"));
                    if (columnExists(rs, "lastName")) cm.setProfLastName(rs.getString("lastName"));
                    if (columnExists(rs, "email")) cm.setProfEmail(rs.getString("email"));
                    if (columnExists(rs, "school")) cm.setSchool(rs.getString("school"));
                    cml.add(cm);
                }
            }
            return cml;
        }

    }


    /*private class to retrieve a list of coursemodel from the resultset returned from the database*/
    private static class CourseModelHashExtractor implements ResultSetExtractor<HashMap<Integer,CourseModel>> {
        @Override
        public HashMap<Integer,CourseModel> extractData(ResultSet rs) throws SQLException, DataAccessException {
            HashMap<Integer,CourseModel> cmh = new HashMap<Integer,CourseModel>();
            if(rs != null && rs.getMetaData().getColumnCount() > 0) {
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
                    if (columnExists(rs, "ano")) cm.setAno(rs.getString("ano"));
                    if (columnExists(rs, "firstName")) cm.setProfFirstName(rs.getString("firstName"));
                    if (columnExists(rs, "lastName")) cm.setProfLastName(rs.getString("lastName"));
                    if (columnExists(rs, "email")) cm.setProfEmail(rs.getString("email"));
                    if (columnExists(rs, "school")) cm.setSchool(rs.getString("school"));
                    cmh.put(cm.getId(),cm);
                }
            }
            return cmh;
        }
    }
}
