package com.backpack.models;

/**
 * Created by Chuntak on 4/5/2017.
 */
public class EnrolledModel {
    /* ID OF STUDENT BEING ENROLLED */
    private int studId;
    /* UNIQUE CODE TO ENROLL IN COURSE */
    private String courseCode;

    /* GETTERS & SETTERS */
    public int getStudId() {
        return studId;
    }

    public void setStudId(int studId) {
        this.studId = studId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
}
