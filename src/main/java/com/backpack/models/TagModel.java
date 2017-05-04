package com.backpack.models;

/**
 * Created by Chuntak on 4/5/2017.
 */
public class TagModel {
    public final static String COURSE = "course";
    public final static String QUIZ = "quiz";
    public final static String QUESTION = "question";
    public final static String NONE = "none";

    private int id;
    private String tagName;
    /*taggableId IS THE ID OF WHAT IS GETTING THE TAG, IT CAN BE A COURSE, QUIZE OR QUESTION */
    private int taggableId;
    private String taggableType;

    public TagModel(){
        taggableType = NONE;
    }


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
