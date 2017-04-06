package com.robertakcs.models;

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
    private int taggableId;
    private String taggableType;

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
