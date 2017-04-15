package com.backpack.dao;

import com.backpack.models.DocumentModel;
import com.backpack.models.PersonModel;
import com.google.cloud.storage.Blob;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Chuntak on 4/12/2017.
 */
public class DocumentDAO extends DAOBase{
    public DocumentModel uploadDocument(DocumentModel dm){
        /*UPLOAD TO CLOUD STORAGE*/
        Blob b = null;
        if(dm.getFile() != null) {
            b = dbs.uploadFile(dm.getFile());
            dm.setBlobName(b.getName());
            dm.setDownloadLink(b.getMediaLink());
            dm.setDateCreated(new Date());
        }
        DocumentModel docModel = updateDocument(dm);
        if(docModel == null){
            dbs.deleteFile(dm.getBlobName());
        }
        return docModel;
    }

    public DocumentModel updateDocument(DocumentModel dm){
        /*UPDATES THE DATABASE*/
        String query = "call update_document(?,?,?,?,?,?,?,?)";
        ArrayList<DocumentModel> dml =  dbs.getJdbcTemplate().query(query, new Object[] { dm.getId(), dm.getCourseId(), dm.getDateCreated(), dm.getTitle(),
                dm.getDescription(), dm.getSection(), dm.getDownloadLink(), dm.getBlobName() }, new DocumentModelExtractor());
        return dml.size() > 0 ? dml.get(0) : null;
    }

    /*private class to retrieve a list of person from the resultset returned from the database*/
    private static class DocumentModelExtractor implements ResultSetExtractor<ArrayList<DocumentModel>> {
        @Override
        public ArrayList<DocumentModel> extractData(ResultSet rs) throws SQLException, DataAccessException {
            ArrayList<DocumentModel> dml = new ArrayList<DocumentModel>();
            if(rs.getMetaData().getColumnCount() > 0) {
                while (rs.next()) {
                    DocumentModel dm = new DocumentModel();
                    if (columnExists(rs, "id")) dm.setId(rs.getInt("id"));
                    if (columnExists(rs, "crsId")) dm.setCourseId(rs.getInt("crsId"));
                    if (columnExists(rs, "dateCreated")) dm.setDateCreated(rs.getDate("dateCreated"));
                    if (columnExists(rs, "title")) dm.setTitle(rs.getString("title"));
                    if (columnExists(rs, "description")) dm.setDescription(rs.getString("description"));
                    if (columnExists(rs, "section")) dm.setSection(rs.getString("section"));
                    if (columnExists(rs, "downloadLink")) dm.setDownloadLink(rs.getString("downloadLink"));
                    if (columnExists(rs, "blobName")) dm.setBlobName(rs.getString("blobName"));
                    dml.add(dm);
                }
            }
            return dml;
        }
    }
}
