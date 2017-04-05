package com.robertakcs.models;

/**
 * Created by Calvin on 4/3/2017.
 */
public class CourseModel {
    private String coursePrefix;
    private String courseNumber;
    private String courseName;
    private String courseCode;
    private int profId;
    private String semester;
    private boolean pub;
    private int id;


    /*Professor attributes*/
    private String profFirstName;
    private String profLastName;
    private String profEmail;

    /*Person attributes*/

    public String getProfFirstName() {
        return profFirstName;
    }

    public void setProfFirstName(String profFirstName) {
        this.profFirstName = profFirstName;
    }

    public String getProfLastName() {
        return profLastName;
    }

    public void setProfLastName(String profLastName) {
        this.profLastName = profLastName;
    }

    public String getProfEmail() {
        return profEmail;
    }

    public void setProfEmail(String profEmail) {
        this.profEmail = profEmail;
    }

    /*Professor attributes*/
    public CourseModel() {
        this.coursePrefix = "";
        this.courseNumber = "";
        this.courseName = "";
        this.courseCode = "";
        this.profId = 0;
        this.semester = "";
        this.pub = false;
        this.id = 0;
    }

    public String getCoursePrefix() {
        return coursePrefix;
    }

    public void setCoursePrefix(String coursePrefix) {
        this.coursePrefix = coursePrefix;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode(){
        return this.courseCode;
    }

    public void setCourseCode(String courseCode){
        this.courseCode = courseCode;
    }

    public int getProfId(){
        return this.profId;
    }

    public void setProfId(int profId){
        this.profId = profId;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public boolean isPub() {
        return pub;
    }

    public boolean getPub() {
        return pub;
    }

    public void setPub(boolean pub) {
        this.pub = pub;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
