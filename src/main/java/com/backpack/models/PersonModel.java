package com.backpack.models;
import java.util.Date;
/**
 * Created by susanlin on 4/1/17.
 */
public class PersonModel {

    /* FIRST NAME OF USER */
    private String firstName;
    /* LAST NAME OF USER */
    private String lastName;
    /* TYPE OF USER i.e. 'stud', 'prof', etc. */
    private String userType;
    /* DATE OF BIRTH */
    private Date dob;
    /* SCHOOL OF USER */
    private String school;
    /* USER's EMAIL */
    private String email;
    /* ID OF USER IN DB */
    private int id;

    public PersonModel(){
        firstName = "";
        lastName = "";
        userType = "";
        dob = new Date();
        school = "";
        email = "";
        id = 0;
    }

    /* GETTERS & SETTERS */
    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getUserType(){
        return userType;
    }

    public Date getDob(){
        return dob;
    }

    public String getSchool(){
        return school;
    }

    public void setFirstName(String name){
        firstName = name;
    }

    public void setLastName(String name){
        lastName = name;
    }

    public void setUserType(String type){
        userType = type;
    }

    public void setDob(Date date){
        dob = date;
    }

    public void setSchool(String sch){
        school = sch;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
