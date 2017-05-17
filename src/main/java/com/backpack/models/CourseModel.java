package com.backpack.models;

/**
 * CourseModel - used to reflect a course
 * Created by Calvin on 4/3/2017.
 */
public class CourseModel {

    /* PAGESIZE FOR PROFESSORS - HOW MANY ITEMS PER PAGE WHEN SEARCHING FOR COURSES */
    public static final int PAGESIZE = 3;

    /* PREFIX OF COURSE ex: 'CSE', 'AMS' */
    private String prefix;
    /* COURSE NUMBER ex: 100, 210, IN CONTEXT OF: 'AMS 100' */
    private String number;
    /* NAME OF COURSE */
    private String name;
    /* SPECIFIC COURSE CODE STUDENTS NEED TO ENROLL */
    private String code;
    /* ID OF THE PROFESSOR IN CHARGE OF COURSE */
    private int profId;
    /* SEMESTER COURSE TAKES PLACE IN FORMAT: "Summer", "Fall", "Winter", "Spring" */
    private String semester;
    /* YEAR THE COURSE TAKES PLACE: 1994, 2017, etc. */
    private String ano;
    /* BOOLEAN DECIDING WHETHER COURSE IS PUBLIC TO OTHER PROFESSORS */
    private boolean pub;
    /* ID OF COURSE IN DB */
    private int id;
    /* SCHOOL THE COURSE BELONGS TO */
    private String school;
    /*THE PAGEGINATION WHERE EACH PAGE IS */
    private int pageNum;
    /*THIS IS USED FOR SEARCHING, WHERE USERS SEARCH ONLY BY NAME (FIRST AND LAST IN 1 TEXTBOX)*/
    private String profName;

    /* PROFESSOR ATTRIBUTES */
    private String profFirstName;
    private String profLastName;
    private String profEmail;

    public CourseModel() {
    }

    /* GETTERS & SETTERS */
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

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getProfName() {
        return profName;
    }

    public void setProfName(String profName) {
        this.profName = profName;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

}
