package com.robertakcs.dao;

import com.robertakcs.models.PersonModel;

import java.math.BigInteger;
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
}
