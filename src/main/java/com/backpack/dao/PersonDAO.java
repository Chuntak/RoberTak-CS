package com.backpack.dao;


import com.backpack.models.PersonModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Chuntak on 4/2/2017.
 */
public class PersonDAO extends DAOBase {
    /**
     * checkUser - check if user exists in db
     * @param pm - person to check
     * @return boolean if they already have account
     */
    public boolean checkUser(PersonModel pm){
        String query = "call get_user(?)";
        List l = dbs.getJdbcTemplate().queryForList(query, new Object[]{pm.getEmail()});
        return l.size() > 0;
    }

    /**
     * updateUser - add/edit a user
     * @param pm - person to update
     * @return id of user
     */
    public int updateUser(PersonModel pm) {
        String query = "call update_user(?,?,?,?,?,?,?)";
        List l = dbs.getJdbcTemplate().queryForList(query, new Object[]{pm.getId(), pm.getEmail(),
                pm.getFirstName(), pm.getLastName(), null, pm.getUserType(), pm.getSchool()});
        return ((BigInteger) ((Map)l.get(0)).get("(LAST_INSERT_ID())")).intValue();
    }

    /**
     * getUserByEmail - gets user by their email from db
     * @param email - email of user
     * @return person with that email
     */
    public PersonModel getUserByEmail(String email) {
        String query = "call get_user(?)";
        ArrayList<PersonModel> al = dbs.getJdbcTemplate().query(query, new Object[] {email} , new PersonModelExtractor());
        return al.get(0);
    }


    /**
     * getEnrolled - gets enrolled students in a course
     * @param crsId - course to get roster for
     * @return list of student data
     */
    public ArrayList<PersonModel> getEnrolled(Integer crsId) {
        String query = "call get_enrolled(0, ?)";
        ArrayList<PersonModel> pml = dbs.getJdbcTemplate().query(query, new Object[]{crsId}, new PersonModelExtractor());
        return pml;
    }

    /*private class to retrieve a list of person from the resultset returned from the database*/
    private static class PersonModelExtractor implements ResultSetExtractor<ArrayList<PersonModel>> {
        @Override
        public ArrayList<PersonModel> extractData(ResultSet rs) throws SQLException, DataAccessException {
            ArrayList<PersonModel> pml = new ArrayList<PersonModel>();
            if(rs != null && rs.getMetaData().getColumnCount() > 0) {
                while (rs.next()) {
                    PersonModel pm = new PersonModel();
                    if (columnExists(rs, "id")) pm.setId(rs.getInt("id"));
                    if (columnExists(rs, "email")) pm.setEmail(rs.getString("email"));
                    if (columnExists(rs, "firstName")) pm.setFirstName(rs.getString("firstName"));
                    if (columnExists(rs, "lastName")) pm.setLastName(rs.getString("lastName"));
                    if (columnExists(rs, "userType")) pm.setUserType(rs.getString("userType"));
                    if (columnExists(rs, "school")) pm.setSchool(rs.getString("school"));
                    pml.add(pm);
                }
            }
            return pml;
        }
    }
}
