package com.backpack.dao;

import com.backpack.models.CourseModel;
import com.backpack.models.TagModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import static com.backpack.models.TagModel.*;

/**
 * Created by Chuntak on 4/5/2017.
 */
public class TagDAO  extends DAOBase{

    /*get all tag, get all tags within a course, quiz, or question*/
    public ArrayList<String> getTag(TagModel tm){
        String query;
        ArrayList tml;
        switch(tm.getTaggableType()){
            case COURSE: /*get tags within a course*/
                query = "call get_taggedCourse(?)";
                tml = dbs.getJdbcTemplate().query(query, new Object[] {tm.getTaggableId()} , new TagNameExtractor());
                return tml;
            case QUESTION: /*get tags within a question*/
                break;
            case QUIZ: /*get tags within a quiz*/
                query = "call get_taggedGradable(?)";
                tml = dbs.getJdbcTemplate().query(query, new Object[] {tm.getTaggableId()} , new TagNameExtractor());
                return tml;
            case NONE: /*get all tags*/
                query = "call get_tag(?)";
                tml = dbs.getJdbcTemplate().query(query, new Object[] {tm.getId()} , new TagNameExtractor());
                return tml;
            default:
                return null;
        }
        return null;
    }

    /**
     * This will call the store procedure in the database update_taggedCourse to store
     * the relation of tags to courses
     * @param tagNames the arraylist of tag names that will be added to the relation
     * @param crsId the course id of what the course will get tagged to
     * @return true if all taggedCourse has been successfully updated, else false
     */
    public boolean updateTaggedCourse(ArrayList<String> tagNames, int crsId) {
        boolean allSuccess = true; /*USED TO RETURN IF ALL UPDATES HAS BEEN A SUCCESS, FOR NOW THERE IS NO WAY TO CHECK IF IT FAILED SO IT IS ALWAYS TRUE*/
        if(tagNames.size() > 0) {
            /*THIS WILL FIRST DELETE ALL THE RELATIONS FROM TAGS AND THAT SUSPECTIC COURSE*/
            String query = "call delete_taggedCourse(?)";
            dbs.getJdbcTemplate().update(query, crsId);
            query = "call update_taggedCourse(?,?)";
            for(String tagName : tagNames) {
                dbs.getJdbcTemplate().update(query, tagName, crsId);
            }
        }
        return allSuccess;
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
            if(rs != null && rs.getMetaData().getColumnCount() > 0) {
                while (rs.next()) {
                    TagModel tm = new TagModel();
                    if (columnExists(rs, "id")) tm.setId(rs.getInt("id"));
                    if (columnExists(rs, "tagName"))tm.setTagName(rs.getString("tagName"));
                    if (columnExists(rs, "taggableId")) tm.setTaggableId(rs.getInt("taggableId"));
                    tml.add(tm);
                }
            }
            return tml;
        }
    }

    public static TagModelExtractor getTagModelExtractor() {
        return new TagModelExtractor();
    }

    private static class TagRelationHashExtractor implements ResultSetExtractor<HashMap<Integer,ArrayList<String>>> {
        @Override
        public HashMap<Integer,ArrayList<String>> extractData(ResultSet rs) throws SQLException, DataAccessException {
            HashMap<Integer,ArrayList<String>> tmh = new HashMap<Integer,ArrayList<String>>();
            if(rs != null && rs.getMetaData().getColumnCount() > 0) {
                while (rs.next()) {
                    if (columnExists(rs, "taggableId") && columnExists(rs, "tagName")){
                        int taggableId = rs.getInt("taggableId");
                        String tagName = rs.getString("tagName");
                        if(tmh.containsKey(taggableId)) {
                            tmh.get(taggableId).add(tagName);
                        }
                        else {
                            ArrayList<String> tns = new ArrayList<String>();
                            tns.add(tagName);
                            tmh.put(taggableId, tns);
                        }
                    }
                }
            }
            return tmh;
        }
    }

    public static TagRelationHashExtractor getTagRelationHashExtractor() {
        return new TagRelationHashExtractor();
    }



    /*private class to extract a list of tags via the resultset returned from the database*/
    private static class TagNameExtractor implements ResultSetExtractor<ArrayList<String>> {
        @Override
        public ArrayList<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
            ArrayList<String> sl = new ArrayList<String>();
            if(rs != null && rs.getMetaData().getColumnCount() > 0) {
                while (rs.next()) {
                    if (columnExists(rs, "tagName")) {
                        String s = rs.getString("tagName");
                        sl.add(s);
                    }
                }
            }
            return sl;
        }
    }

}
