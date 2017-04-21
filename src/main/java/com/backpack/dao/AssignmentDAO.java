package com.backpack.dao;

import com.backpack.models.AssignmentModel;
import com.backpack.models.HWFileModel;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Chuntak on 4/21/2017.
 */
public class AssignmentDAO extends DAOBase {
    public AssignmentModel updateAssignment(AssignmentModel am){
        /*update the database to*/
        String query = "call update_gradable(?,?,?,?,?,?,?,?,?)";
        ArrayList<AssignmentModel> aml = dbs.getJdbcTemplate().query(query ,new Object[] { am.getId(), am.getCourseId(), am.getTitle(), am.getDescription(),
                am.getGradableType(), am.getMaxGrade(), am.getDueDate(), am.getDueTime(), am.getDifficulty()}, new AssignmentModelExtractor());

        /*upload all the hw files*/
        uploadHWFileModels(am.getHWFileModelList());
        return aml.get(0);
    }

    /*gets assignment for course*/
    public ArrayList<AssignmentModel> getAssignments(AssignmentModel am) {
        /*gets the assignments for the course*/
        String query = "call get_gradable(?)";
        ArrayList<AssignmentModel> aml = dbs.getJdbcTemplate().query(query, new Object[] { am.getCourseId() }, new AssignmentModelExtractor());
//        for(AssignmentModel amTmp : aml){ /*gets their corresponding files*/
//            amTmp.setHWFileModelList(getHWFileModels(amTmp.getId()));
//        }
        return aml;
    }

    /*deletes assignment from the database*/
    public boolean deleteAssignment(AssignmentModel am) {
        String query = "call delete_gradable(?)";
        int rowsAffected = dbs.getJdbcTemplate().update(query, am.getId());
        return deleteHWFileModel(0, am.getId());
    }

    /*upload hw files to database*/
    public ArrayList<HWFileModel> uploadHWFileModels ( ArrayList<HWFileModel> hwl ) {
        for(HWFileModel hw : hwl){
            uploadHWFileModel(hw);
        }
        return hwl;
    }

    /*upload hw file to database*/
    public HWFileModel uploadHWFileModel(HWFileModel hw) {
        if (hw.getFile() != null) {
            Blob blob = dbs.uploadFile(hw.getFile()); /*uploads to store*/
            hw.setDownloadLink(blob.getMediaLink());
            hw.setBlobName(blob.getName());
            hw.setFileName(hw.getBlobName().split("|")[0]); /*sets the file name*/
            String query = "call update_hwFile(?,?,?)";
            HWFileModel hwtmp = dbs.getJdbcTemplate().query(query, new Object[] { hw.getId(), hw.getAssignmentId(), hw.getBlobName() }, new HWFileModelExtractor()).get(0); /*update the DB*/
            hw.setId(hwtmp.getId()); /*sets the ID*/
            hw.setFile(null); /*makes the file null, indicate that it is uploaded and we dont return the file to the front*/
            return hw;
        }
        System.err.print("HWfileModel IS NULL");
        return null;
    }

    /*gets the hw file from the database*/
    public ArrayList<HWFileModel> getHWFileModels(HWFileModel hw) {
        return getHWFileModels(hw.getAssignmentId());
    }

    /*get he fiels from the database*/
    public ArrayList<HWFileModel> getHWFileModels(int assignmentId) {
        String query = "call get_hwFile(?)";
        return dbs.getJdbcTemplate().query(query, new Object[] {assignmentId}, new HWFileModelExtractor());
    }

    /*deletss the hw files from the database*/
    public boolean deleteHWFileModel(HWFileModel hw){
        return deleteHWFileModel(hw.getId(), hw.getAssignmentId());
    }

    public boolean deleteHWFileModel(int hwId, int assId){
        String query = "call delete_hwFile(?,?)";
        HWFileModel hw = dbs.getJdbcTemplate().query(query, new Object[] {hwId, assId}, new HWFileModelExtractor()).get(0);
        return dbs.deleteFile(hw.getBlobName());
    }



    /*private class to retrieve a list of person from the resultset returned from the database*/
    private class AssignmentModelExtractor implements ResultSetExtractor<ArrayList<AssignmentModel>> {
        @Override
        public ArrayList<AssignmentModel> extractData(ResultSet rs) throws SQLException, DataAccessException {
            ArrayList<AssignmentModel> aml = new ArrayList<AssignmentModel>();
            if(rs != null && rs.getMetaData().getColumnCount() > 0) {
                while (rs.next()) {
                    AssignmentModel am = new AssignmentModel();
                    if (columnExists(rs, "id")) am.setId(rs.getInt("id"));
                    if (columnExists(rs, "crsId")) am.setCourseId(rs.getInt("crsId"));
                    if (columnExists(rs, "title")) am.setTitle(rs.getString("title"));
                    if (columnExists(rs, "description")) am.setDescription(rs.getString("description"));
                    if (columnExists(rs, "gradableType")) am.setGradableType(rs.getString("gradableType"));
                    if (columnExists(rs, "maxGrade")) am.setMaxGrade(rs.getInt("maxGrade"));
                    if (columnExists(rs, "dueDate")) am.setDueDate(rs.getDate("dueDate"));
                    if (columnExists(rs, "dueTime")) am.setDueTime(rs.getTime("dueTime"));
                    if (columnExists(rs, "difficulty")) am.setDifficulty(rs.getString("difficulty"));
                    aml.add(am);
                }
            }
            return aml;
        }
    }

    /*private class to retrieve a list of person from the resultset returned from the database*/
    private class HWFileModelExtractor implements ResultSetExtractor<ArrayList<HWFileModel>> {
        @Override
        public ArrayList<HWFileModel> extractData(ResultSet rs) throws SQLException, DataAccessException {
            ArrayList<HWFileModel> hwfml = new ArrayList<HWFileModel>();
            if(rs != null && rs.getMetaData().getColumnCount() > 0) {
                while (rs.next()) {
                    HWFileModel hwfm = new HWFileModel();
                    if (columnExists(rs, "id")) hwfm.setId(rs.getInt("id"));
                    if (columnExists(rs, "gradableId")) hwfm.setAssignmentId(rs.getInt("gradableId"));
                    if (columnExists(rs, "blobName")) {
                        hwfm.setBlobName(rs.getString("blobName"));
                        BlobInfo bi = dbs.getBlobInfo(hwfm.getBlobName());
                        hwfm.setDownloadLink(bi.getMediaLink());
                        hwfm.setFileName(hwfm.getBlobName().split("|")[0]);
                        hwfm.setViewLink(dbs.getFileViewLink(hwfm.getBlobName(), false));
                    }
                    hwfml.add(hwfm);
                }
            }
            return hwfml;
        }
    }
}

