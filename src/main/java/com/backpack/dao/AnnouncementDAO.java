package com.backpack.dao;

import com.backpack.models.AnnouncementModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Chuntak on 4/14/2017.
 */
public class AnnouncementDAO extends DAOBase{
    /*PRECONDITION: DATE SHOULD BE NULL WHEN ANNOUNCEMENT IS NEW*/
    public AnnouncementModel updateAnnouncement(AnnouncementModel am){
        /*UPDATES THE DATABASE*/
        if(am.getDateCreated() == null)
            am.setDateCreated(new Date());
        String query = "call update_announcement(?,?,?,?,?)";
        ArrayList<AnnouncementModel> aml =  dbs.getJdbcTemplate().query(query, new Object[] { am.getId(), am.getCourseId(), am.getTitle(),
                am.getDescription(), am.getDateCreated() },  new AnnouncementModelExtractor());
        return aml.size() > 0 ? aml.get(0) : null;
    }

    public ArrayList<AnnouncementModel> getAnnouncement(AnnouncementModel am){
        String query = "call get_announcement(?)";
        ArrayList<AnnouncementModel> aml = dbs.getJdbcTemplate().query(query, new Object[] {am.getCourseId()}, new AnnouncementModelExtractor());
        return aml;
    }

    public boolean deleteAnnouncement(AnnouncementModel am) {
        String query = "call delete_announcement(?)";
        /*returns the number of row affected, in this case it should return 1*/
        return dbs.getJdbcTemplate().update(query, am.getId()) == 1;
    }

    /*private class to retrieve a list of person from the resultset returned from the database*/
    private static class AnnouncementModelExtractor implements ResultSetExtractor<ArrayList<AnnouncementModel>> {
        @Override
        public ArrayList<AnnouncementModel> extractData(ResultSet rs) throws SQLException, DataAccessException {
            ArrayList<AnnouncementModel> aml = new ArrayList<AnnouncementModel>();
            if(rs != null && rs.getMetaData().getColumnCount() > 0) {
                while (rs.next()) {
                    AnnouncementModel am = new AnnouncementModel();
                    if (columnExists(rs, "id")) am.setId(rs.getInt("id"));
                    if (columnExists(rs, "crsId")) am.setCourseId(rs.getInt("crsId"));
                    if (columnExists(rs, "dateCreated")) am.setDate(rs.getTimestamp("dateCreated"));
                    if (columnExists(rs, "title")) am.setTitle(rs.getString("title"));
                    if (columnExists(rs, "description")) am.setDescription(rs.getString("description"));
                    aml.add(am);
                }
            }
            return aml;
        }
    }
}
