package com.robertakcs.models;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by rvtru on 4/5/2017.
 */
public class AssignmentModel {

    private int id;
    private int courseId;
    private String title;
    private String description;
    private String gradableType;
    private double maxGrade;
    private Date dueDate;
    private Time dueTime;
    private String difficulty;

    public AssignmentModel() {
        id = 0;
        courseId = 0;
        title = "";
        description = "";
        gradableType = "";
        maxGrade = 0;
        dueDate = new Date(100000);
        dueTime = new Time(100000);
        difficulty = "";
    }

    public AssignmentModel(int id, int courseId, String title, String description, String gradableType, double maxGrade, Date dueDate, Time dueTime, String difficulty) {
        this.id = id;
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.gradableType = gradableType;
        this.maxGrade = maxGrade;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.difficulty = difficulty;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGradableType() {
        return gradableType;
    }

    public void setGradableType(String gradableType) {
        this.gradableType = gradableType;
    }

    public double getMaxGrade() {
        return maxGrade;
    }

    public void setMaxGrade(double maxGrade) {
        this.maxGrade = maxGrade;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Time getDueTime() {
        return dueTime;
    }

    public void setDueTime(Time dueTime) {
        this.dueTime = dueTime;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
