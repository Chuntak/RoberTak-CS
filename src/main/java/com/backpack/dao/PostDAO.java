package com.backpack.dao;


import com.backpack.models.PersonModel;
import com.backpack.models.PostModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.math.BigInteger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by rvtru on 4/20/2017.
 */
public class PostDAO extends DAOBase {


    /*calls to the database and update post (add/edit)*/
    public int updatePost(PostModel pm) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String timeString = df.format(new Date()).substring(10);
        DateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");
        String startUserDateString = df2.format(new Date());
        startUserDateString = startUserDateString+" "+timeString;

        try {
            pm.setDateCreated(df.parse(startUserDateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String query = "call update_post(?,?,?,?,?,?,?,?)";
        List l = dbs.getJdbcTemplate().queryForList(query, new Object[]{pm.getId(), pm.getParentId(),
                pm.getCrsId(), pm.getAuthorId(), pm.getHeader(), pm.getContent(), pm.isAnon(), pm.getDateCreated()});
        return ((BigInteger) ((Map)l.get(0)).get("(LAST_INSERT_ID())")).intValue();
    }

    /*gets user by email, calls to the database*/
    public ArrayList<PostModel> getPosts(PostModel pm) {
        String query = "call get_posts(?,?)";
        ArrayList<PostModel> al = dbs.getJdbcTemplate().query(query, new Object[] {pm.getId(), pm.getCrsId()} , new PostModelExtractor());
        return al;
    }

    /*private class to retrieve a list of posts from the resultset returned from the database*/
    private static class PostModelExtractor implements ResultSetExtractor<ArrayList<PostModel>> {
        @Override
        public ArrayList<PostModel> extractData(ResultSet rs) throws SQLException, DataAccessException {
            ArrayList<PostModel> pml = new ArrayList<PostModel>();
            if (rs != null && rs.getMetaData().getColumnCount() > 0) {
                while (rs.next()) {
                    PostModel pm = new PostModel();
                    if (columnExists(rs, "id")) pm.setId(rs.getInt("id"));
                    if (columnExists(rs, "anon")) pm.setAnon(rs.getBoolean("anon"));
                    if (columnExists(rs, "firstName")) pm.setFirstName(rs.getString("firstName"));
                    if (columnExists(rs, "lastName")) pm.setLastName(rs.getString("lastName"));
                    if (columnExists(rs, "authorId")) pm.setAuthorId(rs.getInt("authorId"));
                    if (columnExists(rs, "header")) pm.setHeader(rs.getString("header"));
                    if (columnExists(rs, "content")) pm.setContent(rs.getString("content"));
                    if (columnExists(rs, "dateCreated")) pm.setDateCreated(rs.getDate("dateCreated"));
                    if (columnExists(rs, "commentCount")) pm.setCommentCount(rs.getInt("commentCount"));
                    if (columnExists(rs, "content")) pm.setLikes(rs.getInt("likes"));
                    pml.add(pm);
                }
            }
            return pml;
        }
    }
}
