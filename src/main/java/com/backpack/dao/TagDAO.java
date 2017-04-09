package com.backpack.dao;

import com.backpack.models.TagModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.backpack.models.TagModel.*;

/**
 * Created by Chuntak on 4/5/2017.
 */
public class TagDAO  extends DAOBase{

    /*get all tag, get all tags within a course, quiz, or question*/
    public ArrayList<TagModel> getTag(TagModel tm){
        String query;
        ArrayList<TagModel> tml;
        switch(tm.getTaggableType()){
            case COURSE: /*get tags within a course*/
                query = "call get_taggedCourse(?)";
                tml = dbs.getJdbcTemplate().query(query, new Object[] {tm.getTaggableId()} , new TagModelExtractor());
                return tml;
            case QUESTION: /*get tags within a question*/
                break;
            case QUIZ: /*get tags within a quiz*/
                query = "call get_taggedGradable(?)";
                tml = dbs.getJdbcTemplate().query(query, new Object[] {tm.getTaggableId()} , new TagModelExtractor());
                return tml;
            case NONE: /*get all tags*/
                query = "call get_tag(?)";
                tml = dbs.getJdbcTemplate().query(query, new Object[] {tm.getId()} , new TagModelExtractor());
                return tml;
            default:
                return null;
        }
        return null;
    }

    /*we add tags to our database secretly*/
    public void updateTag(TagModel tm){
        String query = "call update_tag(?)";
        dbs.getJdbcTemplate().update(query, tm.getTagName());
    }

    /*private class to extract a list of tags via the resultset returned from the database*/
    private static class TagModelExtractor implements ResultSetExtractor<ArrayList<TagModel>> {
        @Override
        public ArrayList<TagModel> extractData(ResultSet rs) throws SQLException, DataAccessException {
            ArrayList<TagModel> tml = new ArrayList<TagModel>();
            if(rs.getMetaData().getColumnCount() > 0) {
                while (rs.next()) {
                    TagModel tm = new TagModel();
                    tm.setId(rs.getInt("id"));
                    tm.setTagName(rs.getString("tagName"));
                    tml.add(tm);
                }
            }
            return tml;
        }
    }
}
