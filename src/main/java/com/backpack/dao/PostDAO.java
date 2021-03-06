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


    /**
     * updatePost - add/edit a post in db
     * @param pm - post to update
     * @return id of post
     */
    public int updatePost(PostModel pm) {
        /* IF THERE IS NO ID, THEN FORMAT TIME FOR NEW POST */
        if(pm.getId() == 0){
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
        }
        String query = "call update_post(?,?,?,?,?,?,?,?)";
        List l = dbs.getJdbcTemplate().queryForList(query, new Object[]{pm.getId(), pm.getParentId(),
                pm.getCrsId(), pm.getAuthorId(), pm.getHeader(), pm.getContent(), pm.isAnon(), pm.getDateCreated()});
        return ((BigInteger) ((Map)l.get(0)).get("(LAST_INSERT_ID())")).intValue();
    }

    /**
     * updateLikes - updates number of likes on a post
     * @param userId - user who liked/disliked post
     * @param postId - post that was liked
     * @return number of likes
     */
    public int updateLikes(int userId, Integer postId){
        String query = "call update_likes(?,?)";
        return dbs.getJdbcTemplate().update(query, userId, (int)postId);
    }

    /**
     * getPosts - gets posts for a course
     * @param pm - contains the course id
     * @param personId - get posts for a specific person
     * @return list of post models
     */
    public ArrayList<PostModel> getPosts(PostModel pm, int personId) {
        String query = "call get_posts(?,?,?)";
        ArrayList<PostModel> al = dbs.getJdbcTemplate().query(query, new Object[] {pm.getId(), pm.getCrsId(), personId} , new PostModelExtractor());
        return al;
    }

    /**
     * deletePost - deletes a post from db
     * @param pm - post to delete
     * @return boolean if succeeded
     */
    public boolean deletePost(PostModel pm) {
        String query = "call delete_post(?,?,?)";
        pm = dbs.getJdbcTemplate().query(query, new Object[]{pm.getId(), pm.getAuthorId(), pm.getParentId()}, new PostModelExtractor()).get(0);
        return true;
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
                    if (columnExists(rs, "dateCreated")) pm.setDate(rs.getTimestamp("dateCreated"));
                    if (columnExists(rs, "commentCount")) pm.setCommentCount(rs.getInt("commentCount"));
                    if (columnExists(rs, "likes")) pm.setLikes(rs.getInt("likes"));
                    if (columnExists(rs, "liked")) pm.setLiked(rs.getInt("liked"));
                    pml.add(pm);
                }
            }
            return pml;
        }
    }
}
