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

    /**
     * Gets the tags for whatever type that's given in, it supports
     * courses, and quizes so far. For example: if course is the type
     * it will return a list of tags related to that suspecifc type
     * @param tm tag model that contains the ID that the tag is tagged to
     *           and the type of the tag. If the tag type is none
     *           it will return a list of all the tags that are
     *           defined in the database
     * @return a list of tags for that suspecfic tag type
     */
    public ArrayList<String> getTag(TagModel tm){
        return getTag(tm.getTaggableId(), tm.getTaggableType());
    }

    /**
     * Gets the tags for whatever type that's given in, it supports
     * courses, and quizes so far. For example: if course is the type
     * it will return a list of tags related to that suspecifc type
     * @param taggableId the ID that the tag is tagged to
     * @param taggableType The type of the tag, If the tag type is none
     *                     it will return a list of all the tags that are
     *                     defined in the database
     * @return a list of tags for that suspecfic tag type
     */
    public ArrayList<String> getTag(int taggableId, String taggableType){
        String query;
        ArrayList tml;
        switch(taggableType){
            case COURSE: /*GET TAGS WITHIN A COURSE*/
                query = "call get_taggedCourse(?)";
                tml = dbs.getJdbcTemplate().query(query, new Object[] {taggableId} , new TagNameExtractor());
                return tml;
            case QUESTION: /*GET TAGS WITHIN A QUESTION NOT IMPLEMENTED YET*/
                break;
            case QUIZ: /*GET TAGS WITHIN A QUIZ*/
                query = "call get_taggedAssignment(?)";
                tml = dbs.getJdbcTemplate().query(query, new Object[] {taggableId} , new TagNameExtractor());
                return tml;
            case NONE: /*GET ALL TAGS*/
                query = "call get_tag(?)";
                tml = dbs.getJdbcTemplate().query(query, new Object[] {taggableId} , new TagNameExtractor());
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

    /**
     * This will update the database to know the relation between
     * the tag and the gradable, gradable can be an assignment or a quiz
     * @param tagNames the list of tags that the gradable is related to
     * @param gradableId the gradable id that references the gradable
     * @return true
     */
    public boolean updateTaggedGradable(ArrayList<String> tagNames, int gradableId) {
        boolean allSuccess = true; /*USED TO RETURN IF ALL UPDATES HAS BEEN A SUCCESS, FOR NOW THERE IS NO WAY TO CHECK IF IT FAILED SO IT IS ALWAYS TRUE*/
        if(tagNames != null) {
            /*THIS WILL FIRST DELETE ALL THE RELATIONS FROM TAGS AND THAT SUSPECTIC COURSE*/
            String query = "call delete_taggedAssignment(?)";
            dbs.getJdbcTemplate().update(query, gradableId);
            query = "call update_taggedAssignment(?,?)";
            for(String tagName : tagNames) {
                dbs.getJdbcTemplate().update(query, tagName, gradableId);
            }
        }
        return allSuccess;
    }


    /*PRIVATE CLASS TO EXTRACT A LIST OF TAGS VIA THE RESULTSET RETURNED FROM THE DATABASE*/
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

    /**
     * A public getter for the tag model extractor,
     * it extracts a list of tag models from the resultset
     * returned from the database
     * @return a list of tag model
     */
    public static TagModelExtractor getTagModelExtractor() {
        return new TagModelExtractor();
    }

    /*PRIVATE CLASS THAT EXTRACTS THE DATASET OF TAGS FROM THE DB INTO AN HASHMAP*/
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

    /**
     * A public getter for the tag extractor that extracts the resultset into a hashmap
     * @return a hashmap of tags
     */
    public static TagRelationHashExtractor getTagRelationHashExtractor() {
        return new TagRelationHashExtractor();
    }



    /*PRIVATE CLASS TO EXTRACT A LIST OF TAGS VIA THE RESULTSET RETURNED FROM THE DATABASE */
    /*THIS RETURNS A LIST OF STRINGS THAT HAS THE TAG NAMES*/
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
