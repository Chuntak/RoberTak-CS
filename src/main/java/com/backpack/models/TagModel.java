package com.backpack.models;

/**
 * TagModel - used to reflect different tags
 * in the db. Used to describe courses
 * Created by Chuntak on 4/5/2017.
 */
public class TagModel {

    /* THE DIFFERENT KIND OF TAGS */
    public final static String COURSE = "course";
    public final static String QUIZ = "quiz";
    public final static String QUESTION = "question";
    public final static String NONE = "none";

    /* ID OF TAG IN DB */
    private int id;
    /* NAME OF TAG */
    private String tagName;
    /*taggableId IS THE ID OF WHAT IS GETTING THE TAG, IT CAN BE A COURSE, QUIZE OR QUESTION */
    private int taggableId;
    /* THE TYPE OF TAG (course, quiz, question, none) */
    private String taggableType;

    public TagModel(){
        taggableType = NONE;
    }

    /* GETTERS & SETTERS */
    public int getTaggableId() {
        return taggableId;
    }

    public void setTaggableId(int taggableId) {
        this.taggableId = taggableId;
    }

    public String getTaggableType() {
        return taggableType;
    }

    public void setTaggableType(String taggableType) {
        this.taggableType = taggableType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
