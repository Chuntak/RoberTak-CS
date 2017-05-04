package com.backpack.models;

/**
 * Created by Calvin on 4/3/2017.
 */
public class CourseModel {
    public static final int PAGESIZE = 3;

    private String prefix;
    private String number;
    private String name;
    private String code;
    private int profId;
    private String semester;
    private String ano;

    private boolean pub;
    private int id;

    private String school;


    /*THE PAGEGINATION WHERE EACH PAGE IS */
    private int pageNum;

    /*THIS IS USED FOR SEARCHING, WHERE USERS SEARCH ONLY BY NAME (FIRST AND LAST IN 1 TEXTBOX)*/
    private String profName;

    /*Professor attributes*/
    private String profFirstName;
    private String profLastName;
    private String profEmail;
    /*Person attributes*/

    /*Professor attributes*/
    public CourseModel() {
    }

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
