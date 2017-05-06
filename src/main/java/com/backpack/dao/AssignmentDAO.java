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

    /**
     * uploadAssignment - upload a file if exists before updating in db to get file info
     * @param am - assignment model sent from front end to update
     * @return assignment model with new data such as blob name and id
     */
    public AssignmentModel uploadAssignment(AssignmentModel am){
        /* CHECK IF THERE IS NO FILE TO UPLOAD */
        if(am.getHwFile() == null){
            /* RETURN NEW ID TO CONTROLLER */
            return updateAssignment(am);
        }
        /* CHECK IF DELETING OLD FILE - DOESN'T ALLOW REPLACEMENT */
        if(am.getHwFile() == null && am.getHwBlobName() != null && !am.getHwBlobName().equals("")) {
            dbs.deleteFile(am.getHwBlobName());
        }
        Blob b = null;
        /* CHECK IF FILE TO UPLOAD */
        if(am.getHwFile() != null) {
            b = dbs.uploadFile(am.getHwFile());
            am.setHwBlobName(b.getName());
            am.setHwDownloadLink(b.getMediaLink());
        }
        /* UPDATE DB WITH FILE INFO */
        AssignmentModel asgmtModel = updateAssignment(am);
        /* CHECK THAT UPDATE WORKED */
        if(asgmtModel == null){
            dbs.deleteFile(am.getHwBlobName());
        }
        /* DON'T FORGET TO RETURN DOWNLOAD LINK */
        asgmtModel.setHwDownloadLink(am.getHwDownloadLink());
        /* RETURN NEW DATA TO CONTROLLER */
        return asgmtModel;
    }

    public AssignmentModel uploadSubmission(AssignmentModel am, int userId){
        /* CHECK IF THERE IS NO FILE TO UPLOAD */
        if(am.getHwFile() == null){
            /* RETURN NULL ? */
            return null;
        }
        /* CHECK IF DELETING OLD FILE */
        if(am.getSubmissionBlobName() != null && !am.getSubmissionBlobName().equals("")) {
            dbs.deleteFile(am.getSubmissionBlobName());
        }
        Blob b = null;
        /* CHECK IF FILE TO UPLOAD */
        if(am.getHwFile() != null) {
            b = dbs.uploadFile(am.getHwFile());
            am.setHwBlobName(b.getName());
            am.setHwDownloadLink(b.getMediaLink());
        }
        /* UPDATE DB WITH FILE INFO */
        AssignmentModel asgmtModel = updateSubmission(am, userId);
        /* CHECK THAT UPDATE WORKED */
        if(asgmtModel == null){
            dbs.deleteFile(am.getHwBlobName());
        }
        /* DON'T FORGET TO RETURN DOWNLOAD LINK */
        //asgmtModel.setHwDownloadLink(am.getHwDownloadLink());
        /* RETURN NEW DATA TO CONTROLLER */
        return asgmtModel;
    }

    /**
     * updateSubmission - updates Submission in DB
     * @param am - submission to add/update
     *           - may need to delete old submission from storage
     *             if query returns a blobName of old submission
     * @return boolean if successful
     */
    public AssignmentModel updateSubmission(AssignmentModel am, int userId){
        /* DATABASE QUERY */
        String query = "call update_submission(?,?,?,?)";
        /* EXECUTE QUERY AND RETURN LIST OF ASSIGNMENT OBJECTS */
        ArrayList<AssignmentModel> aml = dbs.getJdbcTemplate().query(query ,new Object[] { 0, am.getId(), userId, am.getHwBlobName()}, new AssignmentModelExtractor());

        /* RETURN NEWLY CREATED ASSIGNMENT WITH ID */
        return aml.size() > 0 ? aml.get(0) : null;
    }

    /**
     * updateAssignment - updates Assignment in DB
     * @param am - assignment to add/update
     * @return assignment with ID and possible file info
     */
    public AssignmentModel updateAssignment(AssignmentModel am){
        /* DATABASE QUERY */
        String query = "call update_gradable(?,?,?,?,?,?,?,?,?)";
        /* EXECUTE QUERY AND RETURN LIST OF ASSIGNMENT OBJECTS */
        ArrayList<AssignmentModel> aml = dbs.getJdbcTemplate().query(query ,new Object[] { am.getId(), am.getCourseId(), am.getTitle(), am.getDescription(),
                am.getGradableType(), am.getMaxGrade(), am.getDueDate(), am.getDifficulty(), am.getHwBlobName()}, new AssignmentModelExtractor());

        /* RETURN NEWLY CREATED ASSIGNMENT WITH ID */
        return aml.size() > 0 ? aml.get(0) : null;
    }

    /*gets assignment for course*/
    public ArrayList<AssignmentModel> getAssignments(Integer crsId, String gradableType, int userId) {
        /*gets the assignments for the course*/
        String query = "call get_gradable(?, ?, ?)";
        ArrayList<AssignmentModel> aml = dbs.getJdbcTemplate().query(query, new Object[] { crsId, gradableType, userId }, new AssignmentModelExtractor());
        return aml;
    }

    /*deletes assignment from the database*/
    public int deleteAssignment(AssignmentModel am) {
        String query = "call delete_gradable(?)";
        int rowsAffected = dbs.getJdbcTemplate().update(query, am.getId());

        /* DELETE FILE FROM STORAGE IF ANY */
        if(am.getHwBlobName() == null || am.getHwBlobName().equals("")){
            return am.getId();
        }
        dbs.deleteFile(am.getHwBlobName());
        return am.getId();
        //return deleteHWFileModel(0, am.getId());
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
                    if (columnExists(rs, "dueDate")) am.setDate(rs.getTimestamp("dueDate"));
                    if (columnExists(rs, "difficulty")) am.setDifficulty(rs.getString("difficulty"));
                    if (columnExists(rs, "submittable")) am.setSubmittable(rs.getBoolean("submittable"));
                    if (columnExists(rs, "submission"))
                        am.setSubmissionBlobName(rs.getString("submission"));
                    if (columnExists(rs, "blobName")) am.setHwBlobName(rs.getString("blobName"));
                    if (am.getHwBlobName() != null && !am.getHwBlobName().equals("")) {
                        /* IF THERE IS A BLOB NAME, GET THE DOWNLOAD LINK */
//                        Blob b = dbs.getBlob(am.getHwBlobName());
//                        String link = b.getMediaLink();
                        try{ am.setHwFileName(am.getHwBlobName().split("\\|")[1]); }
                        catch (IndexOutOfBoundsException e) { System.err.println("Filename not formatted correctly."); }
                        am.setHwDownloadLink(dbs.getFileViewLink(am.getHwBlobName(), false));
                    }
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
                        Blob bi = dbs.getBlob(hwfm.getBlobName());
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

