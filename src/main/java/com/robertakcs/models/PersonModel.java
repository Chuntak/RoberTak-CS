package com.robertakcs.models;
import java.util.Date;
/**
 * Created by susanlin on 4/1/17.
 */
public class PersonModel {

    private String firstName;
    private String lastName;
    private String userType;
    private Date dob;
    private String school;


    public PersonModel(){
        firstName = "";
        lastName = "";
        userType = "";
        dob = new Date();
        school = "";
    }

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


}
