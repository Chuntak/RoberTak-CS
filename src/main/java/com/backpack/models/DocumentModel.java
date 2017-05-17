package com.backpack.models;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * DocumentModel - used to reflect a course
 * Created by rvtru on 4/5/2017.
 */
public class DocumentModel {

    /* ID OF DOCUMENT IN DB */
    private int id;
    /* ID OF COURSE THE DOCUMENT BELONGS TO */
    private int courseId;
    /* DATE THE DOCUMENT WAS CREATED */
    private Date dateCreated;
    /* TITLE OF THE DOCUMENT */
    private String title;
    /* DESCRIPTION OF THE DOCUMENT */
    private String description;
    /* FILE UPLOADED BY PROF  & FILE ATTRIBUTES*/
    private MultipartFile file;
    private String downloadLink;
    private String blobName;
    private String fileName;
    private String section;
    private String viewLink;


    public DocumentModel() {
    }
    /* GETTERS & SETTERS */
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public String getBlobName() {
        return blobName;
    }

    public void setBlobName(String blobName) {
        this.blobName = blobName;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getViewLink() {
        return viewLink;
    }

    public void setViewLink(String viewLink) {
        this.viewLink = viewLink;
    }
}

