package com.backpack.models;

/**
 * Created by Admin on 5/4/2017.
 */
public class GradeModel {

    private String firstName;
    private String lastName;
    private String email;
    private String submissionFile;
    private int gradableId;
    private int courseId;
    private int grade;
    private int stdId;

    public String getSubmissionFile() {
        return submissionFile;
    }

    public void setSubmissionFile(String submissionFile) {
        this.submissionFile = submissionFile;
    }

    public int getGradableId() {
        return gradableId;
    }

    public void setGradableId(int gradableId) {
        this.gradableId = gradableId;
    }

    public int getStdId() {
        return stdId;
    }

    public void setStdId(int stdId) {
        this.stdId = stdId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {

        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
