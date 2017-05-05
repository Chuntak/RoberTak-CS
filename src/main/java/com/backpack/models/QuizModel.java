package com.backpack.models;

import java.util.Date;

/**
 * Created by Admin on 5/5/2017.
 */
public class QuizModel {
    /* ID OF QUIZ IN DB */
    private int id;
    /* ID OF COURSE THE QUIZ BELONGS TO */
    private int courseId;
    /* TITLE OF THE ANNOUNCEMENT */
    private String title;
    /* DATE THE QUIZ IS DUE*/
    private Date dueDate;
    /* THE TYPE SHOWN IN THE DATABASE*/
    private String gradableType;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getGradableType() {
        return gradableType;
    }

    public void setGradableType(String gradableType) {
        this.gradableType = gradableType;
    }

}
