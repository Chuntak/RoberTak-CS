package com.backpack.models;

/**
 * Created by Calvin on 4/3/2017.
 */
public class CourseModel {
    private String prefix;
    private String number;
    private String name;
    private String code;
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
        this.prefix = "";
        this.number = "";
        this.name = "";
        this.code = "";
        this.profId = 0;
        this.semester = "";
        this.pub = false;
        this.id = 0;
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
}
