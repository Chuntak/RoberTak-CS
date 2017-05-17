package com.backpack.models;

/**
 * Created by Calvin on 5/4/2017.
 */
public class GradeModel {

    private String firstName;
    private String lastName;
    private String email;
    private int gradableId;
    private int courseId;
    private int grade;
    private int id;



    /*FOR THE SUBMISSION FILES*/

    /*Blob name gotten from the database*/
    private String blobName;
    /*The name of the submitted file*/
    private String fileName;
    /*The link to download the file*/
    private String downloadLink;

    public int getGradableId() {return gradableId;}

    public void setGradableId(int gradableId) {this.gradableId = gradableId;}

    public int getId() {return id;}
    public void setId(int studId) {this.id = studId;}

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

    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getBlobName() {return blobName;}
    public void setBlobName(String blobName) {this.blobName = blobName;}

    public String getFileName() {return fileName;}
    public void setFileName(String fileName) {this.fileName = fileName;}

    public String getDownloadLink() {return downloadLink;}
    public void setDownloadLink(String downloadLink) {this.downloadLink = downloadLink;}

}
