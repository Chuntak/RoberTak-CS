package com.robertakcs.dao;


import com.robertakcs.models.PersonModel;
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
    public boolean checkUser(PersonModel pm){
        String query = "call get_user(?)";
        List l = dbs.getJdbcTemplate().queryForList(query, new Object[]{pm.getEmail()});
        return l.size() > 0;
    }
    public int updateUser(PersonModel pm) {
        String query = "call update_user(?,?,?,?,?,?,?)";
        List l = dbs.getJdbcTemplate().queryForList(query, new Object[]{pm.getId(), pm.getEmail(),
                pm.getFirstName(), pm.getLastName(), null, pm.getUserType(), pm.getSchool()});
        return ((BigInteger) ((Map)l.get(0)).get("(LAST_INSERT_ID())")).intValue();
    }

    public PersonModel getUserByEmail(String email) {
        String query = "call get_user(?)";
        ArrayList<PersonModel> al = dbs.getJdbcTemplate().query(query, new Object[] {email} , new PersonModelExtractor());
        return al.get(0);
    }

    private static class PersonModelExtractor implements ResultSetExtractor<ArrayList<PersonModel>> {
        @Override
        public ArrayList<PersonModel> extractData(ResultSet rs) throws SQLException, DataAccessException {
            ArrayList<PersonModel> pml = new ArrayList<PersonModel>();
            while(rs.next()){
                PersonModel pm = new PersonModel();
                pm.setId(rs.getInt("id"));
                pm.setEmail(rs.getString("email"));
                pm.setFirstName(rs.getString("firstName"));
                pm.setLastName(rs.getString("lastName"));
                pm.setUserType(rs.getString("userType"));
                pm.setSchool(rs.getString("school"));
                pml.add(pm);
            }
            return pml;
        }
    }
}
