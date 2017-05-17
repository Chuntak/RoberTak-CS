package com.backpack.models;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by rvtru on 4/5/2017.
 * An Announcement Model class to reflect Announcements in the database
 * and
 */
public class AnnouncementModel {

    /* ID OF ANNOUNCEMENT IN DB */
    private int id;
    /* ID OF COURSE THE ANNOUNCEMENT BELONGS TO */
    private int courseId;
    /* TITLE OF THE ANNOUNCEMENT */
    private String title;
    /* CONTENT OF THE ANNOUNCEMENT */
    private String description;
    /* DATE THE ANNOUNCEMENT WAS CREATED */
    private Date dateCreated;

    /*
     * Default Constructor
     * - values don't matter because data will be mapped
     *   using mutator methods
     */
    public AnnouncementModel() {
        this.id = 0;
        this.courseId = 0;
        title = "";
        description = "";
        dateCreated = new Date();
    }

    /*
     * Parameterized Constructor
     * - to properly map objects retrieved from database
     */
    public AnnouncementModel(int id, int courseId, String title, String description, Date dateCreated) {
        this.id = id;
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.dateCreated = dateCreated;
    }

    /* GETTER & SETTER METHODS */
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

    /**
     * setDate - special setter to map TimeStamp data from database
     * @param time
     */
    public void setDate(Timestamp time){
        this.dateCreated = new Date(time.getTime());
    }
}
