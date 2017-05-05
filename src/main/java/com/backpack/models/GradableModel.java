package com.backpack.models;

import java.util.Date;

/**
 * Created by susanlin on 5/5/17.
 */
public class GradableModel {

    private String title;
    private double maxGrade;
    private double minGrade;
    private double standardD;
    private double avg;
    private double median;
    private int courseId;
    private int stdId;
    private String description;
    private int id;
    private String gradableType;
    private Date dueDate;
    private String difficulty;
    private String blobName;

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

    public double getStandardD() {
        return standardD;
    }

    public void setStandardD(double standardD) {
        this.standardD = standardD;
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
}
