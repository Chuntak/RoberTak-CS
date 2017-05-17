package com.backpack.models;

import org.springframework.web.multipart.MultipartFile;

/**
 * HWFileModel - used to reflect the data of
 * an uploaded file from professor for an assignment
 * Created by Chuntak on 4/21/2017.
 */
public class HWFileModel {

    /* ID OF ASSIGNMENT THE FILE IS FOR */
    private int assignmentId;
    /* THE FILE UPLOADED BY PROF */
    private MultipartFile file;
    /* BLOBNAME (KEY) FOR FILE IN STORAGE */
    private String blobName;
    /* NAME OF FILE */
    private String fileName;
    /* LINK TO DOWNLOAD FILE */
    private String downloadLink;
    /* THE VISIBLE LINK OF FILE */
    private String viewLink;
    /* ID OF HWFILE IN DB */
    private int id;

    /* GETTERS & SETTERS */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
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
