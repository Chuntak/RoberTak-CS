package com.backpack.dao;

import com.backpack.models.GradableModel;
import com.backpack.models.GradeModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Calvin on 5/4/2017.
 */
public class GradeDAO extends DAOBase{

    /*  GET INFORMATION FROM DB FOR: STUDENT INFO, SUBMISSION FILE */
    public ArrayList<GradeModel> getGrade(GradeModel gm){
        String query = "call get_grade(?,?,?)";
        return dbs.getJdbcTemplate().query(query, new Object[] { 0,gm.getCourseId(), gm.getGradableId() }, new GradeModelExtractor());
    }

    /*UPDATES THE DATABASE GRADE*/
    public GradeModel updateGrade(GradeModel gm){
        String query = "call update_grade(?,?,?)";
        ArrayList<GradeModel> gml =  dbs.getJdbcTemplate().query(query, new Object[] { gm.getId(), gm.getGradableId(),gm.getGrade() }, new GradeDAO.GradeModelExtractor());
        return gml.size() > 0 ? gml.get(0) : null;
    }

    /* RETRIEVE RESULTS FROM DB STORED PROCEDURE (QUERY) */
    private static class GradeModelExtractor implements ResultSetExtractor<ArrayList<GradeModel>> {

        public ArrayList<GradeModel> extractData(ResultSet rs) throws SQLException, DataAccessException {
            ArrayList<GradeModel> gml = new ArrayList<GradeModel>();
            if(rs != null && rs.getMetaData().getColumnCount() > 0){
                while (rs.next()) {
                    GradeModel gm = new GradeModel();
                    if(columnExists(rs, "firstName")) gm.setFirstName(rs.getString("firstName"));
                    if(columnExists(rs, "lastName")) gm.setLastName(rs.getString("lastName"));
                    if(columnExists(rs, "email")) gm.setEmail(rs.getString("email"));
                    if(columnExists(rs, "submissionFile")) gm.setSubmissionFile(rs.getString("submissionFile"));
                    if(columnExists(rs,"gradableId")) gm.setGradableId(rs.getInt("gradableId"));
                    if(columnExists(rs,"crsId")) gm.setCourseId(rs.getInt("crsId"));
                    if(columnExists(rs, "grade")) gm.setGrade(rs.getInt("grade"));
                    if(columnExists(rs,"id")) gm.setId(rs.getInt("id"));
                    gml.add(gm);
                }
            }
            return gml;
        }
    }

    /*  GET INFORMATION FROM DB FOR: TITLE, GRADES STATISTICS */
    public ArrayList<GradableModel> getGradable(GradableModel gm){
        String query = "call get_gradable(?,?,?)";
        return dbs.getJdbcTemplate().query(query, new Object[] { gm.getCourseId(), gm.getTitle(), gm.getStdId() }, new GradableModelExtractor());
    }

    /*UPDATES THE DATABASE GRADABLE*/
    public GradableModel updateGradable(GradableModel gm){
//        gm.setDateCreated(new Date()); //temp
        String query = "call update_gradable(?,?,?,?,?,?,?,?,?,?)";
        ArrayList<GradableModel> gml =  dbs.getJdbcTemplate().query(query, new Object[] { gm.getId(), gm.getCourseId(), gm.getTitle(),
                gm.getDescription(), gm.getGradableType(), gm.getMaxGrade(), gm.getDueDate(), gm.getDifficulty(), gm.getBlobName(), 0}, new GradeDAO.GradableModelExtractor());
        return gml.size() > 0 ? gml.get(0) : null;
    }
    /* DELETE A GRADABLE*/
    public boolean deleteGradable(GradableModel gm) {
        String query = "call delete_gradable(?)";
        int rowAffect = dbs.getJdbcTemplate().update(query, gm.getId());
        return true;
    }

    /* RETRIEVE RESULTS FROM DB STORED PROCEDURE (QUERY) */
    private static class GradableModelExtractor implements ResultSetExtractor<ArrayList<GradableModel>> {

        public ArrayList<GradableModel> extractData(ResultSet rs) throws SQLException, DataAccessException {
            ArrayList<GradableModel> gbml = new ArrayList<GradableModel>();
            if(rs != null && rs.getMetaData().getColumnCount() > 0){
                while (rs.next()) {
                    GradableModel gbm = new GradableModel();
                    if(columnExists(rs, "id")) gbm.setId(rs.getInt("id"));
                    if(columnExists(rs, "title")) gbm.setTitle(rs.getString("title"));
                    if(columnExists(rs, "maxGrade")) gbm.setMaxGrade(rs.getInt("maxGrade"));
                    if(columnExists(rs, "dueDate")) gbm.setDate(rs.getTimestamp("dueDate"));
                    if(columnExists(rs, "description")) gbm.setDescription(rs.getString("description"));
                    if(columnExists(rs, "gradableType")) gbm.setGradableType(rs.getString("gradableType"));

//                    if(columnExists(rs, "avg"));
                    gbml.add(gbm);
                }
            }
            return gbml;
        }
    }




}
