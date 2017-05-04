package com.backpack.dao;

import com.backpack.models.CourseModel;
import com.backpack.models.SyllabusModel;
import com.google.cloud.storage.Blob;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Chuntak on 4/12/2017.
 */
public class SyllabusDAO extends DAOBase {

    /*uploads and stores syllabus into the database*/
    public SyllabusModel uploadSyllabus(SyllabusModel sm){
        if(sm.getFile() != null) {
            Blob blob = dbs.uploadFile(sm.getFile()); /*uploads to store*/
            sm.setDownloadLink(blob.getMediaLink()); /*sets download link*/
            sm.setBlobName(blob.getName()); /*sets blob name*/
            sm.setFileName(sm.getBlobName().split("|")[0]); /*sets the file name*/
            String query = "call update_syllabus(?,?,?)";
            SyllabusModel smTemp  = dbs.getJdbcTemplate().query(query, new Object[] {sm.getCourseId(), sm.getDownloadLink(), sm.getBlobName()},
                    new SyllabusModelExtractor()); /*sends it to the database*/
            if(smTemp != null && (smTemp.getBlobName() != null || !smTemp.getBlobName().equals(""))){ /*check if theres previous syllabus, if there is delete it from stoarge*/
                dbs.deleteFile(smTemp.getBlobName());
            }
            sm.setViewLink(dbs.getFileViewLink(sm.getBlobName(), true));
            sm.setFile(null); /*CLEAR THIS OUT SO WE DONT RETURN IT*/
            return sm;
        }
        return sm;
    }

    public SyllabusModel getSyllabus(SyllabusModel sm) {
        String query = "call get_syllabus(?)";
        sm = dbs.getJdbcTemplate().query(query, new Object[] {sm.getCourseId()}, new SyllabusModelExtractor());
        if(sm != null) {
            sm.setViewLink(dbs.getFileViewLink(sm.getBlobName(), true));
            return sm;
        } else {
            return null;
        }
    }

    public boolean deleteSyllabus(SyllabusModel sm) {
        String query = "call delete_syllabus(?)";
        sm = dbs.getJdbcTemplate().query(query, new Object[] {sm.getCourseId()}, new SyllabusModelExtractor()); /*delete from database*/
        if(sm.getBlobName() != null || !sm.getBlobName().equals("")){ /*Delete from storage*/
            dbs.deleteFile(sm.getBlobName());
            return true;
        }
        return false;
    }


    /*private class to retrieve syllabus from the resultset returned from the database*/
    private static class SyllabusModelExtractor implements ResultSetExtractor<SyllabusModel> {
        @Override
        public SyllabusModel extractData(ResultSet rs) throws SQLException, DataAccessException {
            if(rs != null && rs.getMetaData().getColumnCount() > 0) {
                SyllabusModel sm = null;
                if (rs.next()) {
                    sm = new SyllabusModel();
                    if (columnExists(rs, "sylBlobName")) {
                        sm.setBlobName(rs.getString("sylBlobName"));
                        if(sm.getBlobName() != null) {
                            try {
                                sm.setFileName(sm.getBlobName().split("\\|")[1]);
                            } catch (IndexOutOfBoundsException e) {
                                System.err.println("Filename not formated correctly.");
                            }
                        }
                    }
                    if (columnExists(rs, "sylLink")) sm.setDownloadLink(rs.getString("sylLink"));
                }
                return sm;
            }
            return null;
        }
    }
}
