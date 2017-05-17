package com.backpack.dao;

import com.backpack.models.AnnouncementModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/** Announcement Data Access Object
 * - queries the database and maps data
 * from queries into models
 * Created by Chuntak on 4/14/2017.
 */
public class AnnouncementDAO extends DAOBase{
    /*PRECONDITION: DATE SHOULD BE NULL WHEN ANNOUNCEMENT IS NEW*/

    /**
     * updateAnnouncement - called to Add/Update
     * @param am - the announcement data to be updated in db
     * @return the new am
     */
    public AnnouncementModel updateAnnouncement(AnnouncementModel am){
        /*UPDATES THE DATABASE*/
        /* IF NO DATE, ITS NEW SO SET CREATION DATE */
        if(am.getDateCreated() == null)
            am.setDateCreated(new Date());
        /* MAKE QUERY */
        String query = "call update_announcement(?,?,?,?,?)";
        /* SET PARAMS IN QUERY AND MAP RESULT SET INTO LIST OF MODELS */
        ArrayList<AnnouncementModel> aml =  dbs.getJdbcTemplate().query(query, new Object[] { am.getId(), am.getCourseId(), am.getTitle(),
                am.getDescription(), am.getDateCreated() },  new AnnouncementModelExtractor());
        /* RETURN MODEL */
        return aml.size() > 0 ? aml.get(0) : null;
    }

    /**
     * getAnnouncement - queries db for announcements for course
     * @param am - contains the course id of current course
     * @return aml - list of models
     */
    public ArrayList<AnnouncementModel> getAnnouncement(AnnouncementModel am){
        String query = "call get_announcement(?)";
        ArrayList<AnnouncementModel> aml = dbs.getJdbcTemplate().query(query, new Object[] {am.getCourseId()}, new AnnouncementModelExtractor());
        return aml;
    }

    /**
     * deleteAnnouncement - deletes an announcement from db
     * @param am - contains the id of the announcement to delete
     * @return boolean if succeeded
     */
    public boolean deleteAnnouncement(AnnouncementModel am) {
        String query = "call delete_announcement(?)";
        /*returns the number of row affected, in this case it should return 1*/
        return dbs.getJdbcTemplate().update(query, am.getId()) == 1;
    }

    /*private class to return models mapped from the resultset returned from the database*/
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
