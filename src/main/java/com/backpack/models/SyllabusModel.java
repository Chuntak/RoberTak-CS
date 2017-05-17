package com.backpack.models;

import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

/**
 * Syllabus Model - used to reflect a syllabus
 * provided by the professor
 * Created by Chuntak on 4/9/2017.
 */
public class SyllabusModel {
    /* ID OF COURSE THE SYLLABUS IS FOR */
    private int courseId;
    /* DATE THE SYLLABUS WAS UPLOADED */
    private Date dateCreated;
    /* TITLE OF THE SYLLABUS */
    private String title;
    /* DESCRIPTION OF SYLLABUS */
    private String description;
    /* FILE BEING UPLOADED */
    private MultipartFile file;

    /* FILE ATTRIBUTES */
    private String blobName;
    private String fileName;
    private String downloadLink;
    private String viewLink;

    /* GETTERS & SETTERS */
    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBlobName() {
        return blobName;
    }

    public void setBlobName(String blobName) {
        this.blobName = blobName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public String getViewLink() {
        return viewLink;
    }

    public void setViewLink(String viewLink) {
        this.viewLink = viewLink;
    }
}
