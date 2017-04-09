package com.backpack.models;

import java.sql.Date;

/**
 * Created by rvtru on 4/5/2017.
 */
public class DocumentModel {

    private int id;
    private int courseId;
    private Date dateCreated;
    private String title;
    private String description;

    public DocumentModel() {
        this.id = 0;
        this.courseId = 0;
        title = "";
        description = "";
        dateCreated = new Date(10000000);
    }

    public DocumentModel(int id, int courseId, String title, String description, Date dateCreated) {
        this.id = id;
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.dateCreated = dateCreated;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
