package com.backpack.models;

import java.util.Date;

/**
 * GradableModel - used to reflect a gradable item
 * hws, extra credit, quizzes, attendance, other
 *
 * Could have different values left empty depending on
 * context of use.
 * Created by susanlin on 5/5/17.
 */

public class GradableModel {

    /* TITLE OF THE GRADABLE ITEM */
    private String title;
    /* MAX POSSIBLE GRADE */
    private double maxGrade;
    /* HIGHEST GRADE RECEIVED BY STUDENT */
    private double highestGrade;
    /* LOWEST GRADE SCORED FROM CLASS */
    private double minGrade;
    /* AVG GRADE RECEIVED FOR GRADABLE BY STUDENTS */
    private double avg;
    /* MEDIAN GRADE RECEIVED FOR GRADABLE BY STUDENTS */
    private double median;
    /* STANDARD DEVIATION OF GRADES FOR GRADABLE */
    private double stdDev;
    /* ID OF COURSE THE GRADABLE IS FROM */
    private int courseId;
    /* ID OF STUDENT THE GRADABLE BELONGS TO */
    private int stdId;
    /* DESCRIPTION OF GRADABLE ITEM */
    private String description;
    /* ID OF GRADABLE ITEM IN DB */
    private int id;
    /* TYPE OF GRADABLE ITEM */
    private String gradableType;
    /* DUE DATE OF GRADABLE ITEM */
    private Date dueDate;
    /* DIFFICULTY OF GRADABLE ITEM */
    private String difficulty;
    /* BLOBNAME IS A REFERENCE TO ANY UPLOADED FILE FOR GRADABLE ITEM */
    private String blobName;

    /* GETTERS & SETTERS */
    public String getBlobName() {
        return blobName;
    }

    public void setBlobName(String blobName) {
        this.blobName = blobName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGradableType() {
        return gradableType;
    }

    public void setGradableType(String gradableType) {
        this.gradableType = gradableType;
    }

    public Date getDueDate() {
        return dueDate;
    }

    /* SETTER USE BY MAPPING FROM CLIENT */
    public void setDueDate(long dueDate) {
        this.dueDate = new Date(dueDate);
    }
    /* SETTER TO/FROM DB*/
    public void setDate(java.sql.Timestamp dueDate){
        long l = dueDate.getTime();
        Date d = new Date(l);
        this.dueDate = d;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getSubmission() {
        return submission;
    }

    public void setSubmission(int submission) {
        this.submission = submission;
    }

    private int submission;


    public int getStdId() {
        return stdId;
    }

    public void setStdId(int stdId) {
        this.stdId = stdId;
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

    public double getMaxGrade() {
        return maxGrade;
    }

    public void setMaxGrade(double maxGrade) {
        this.maxGrade = maxGrade;
    }

    public double getMinGrade() {
        return minGrade;
    }

    public void setMinGrade(double minGrade) {
        this.minGrade = minGrade;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public double getMedian() {
        return median;
    }

    public void setMedian(double median) {
        this.median = median;
    }

    public double getHighestGrade() {return highestGrade;}

    public void setHighestGrade(double highestGrade) {this.highestGrade = highestGrade;}

    public double getStdDev() {return stdDev;}

    public void setStdDev(double stdDev) {this.stdDev = stdDev;}
}
