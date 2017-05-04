package com.backpack.models;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.ArrayList;

/**
 * Created by rvtru on 4/5/2017.
 */
public class AssignmentModel {

    /* ID IN DB */
    private int id;
    /* ID OF COURSE THAT THIS ASSIGNMENT BELONGS TO */
    private int courseId;
    private String title;
    private String description;
    private String gradableType;
    private double maxGrade;
    private Date dueDate;
    private String difficulty;

    private MultipartFile hwFile;
    private String hwBlobName;
    private String hwFileName;
    private String hwDownloadLink;
    private String hwViewLink;
    private int hwId;


    private ArrayList<HWFileModel> hwFileModelList;

    public AssignmentModel() {
    }

    public AssignmentModel(int id, int courseId, String title, String description, String gradableType, double maxGrade, Date dueDate,  String difficulty) {
        this.id = id;
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.gradableType = gradableType;
        this.maxGrade = maxGrade;
        this.dueDate = dueDate;
        this.difficulty = difficulty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
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

    public String getGradableType() {
        return gradableType;
    }

    public void setGradableType(String gradableType) {
        this.gradableType = gradableType;
    }

    public double getMaxGrade() {
        return maxGrade;
    }

    public void setMaxGrade(double maxGrade) {
        this.maxGrade = maxGrade;
    }

    public Date getDueDate() {
        return dueDate;
    }

    /* SETTER USED BY MAPPING FROM CLIENT SIDE */
    public void setDueDate(long dueDate) {
        this.dueDate = new Date(dueDate);
    }

    /* SETTER WHEN USED TO MAPPING TO/FROM DB */
    public void setDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public ArrayList<HWFileModel> getHWFileModelList() {
        return hwFileModelList;
    }

    public void setHWFileModelList(ArrayList<HWFileModel> hwFileModelList) {
        this.hwFileModelList = hwFileModelList;
    }

    public MultipartFile getHwFile() {
        return hwFile;
    }

    public void setHwFile(MultipartFile hwFile) {
        this.hwFile = hwFile;
    }

    public String getHwBlobName() {
        return hwBlobName;
    }

    public void setHwBlobName(String hwBlobName) {
        this.hwBlobName = hwBlobName;
    }

    public String getHwFileName() {
        return hwFileName;
    }

    public void setHwFileName(String hwFileName) {
        this.hwFileName = hwFileName;
    }

    public String getHwDownloadLink() {
        return hwDownloadLink;
    }

    public void setHwDownloadLink(String hwDownloadLink) {
        this.hwDownloadLink = hwDownloadLink;
    }

    public String getHwViewLink() {
        return hwViewLink;
    }

    public void setHwViewLink(String hwViewLink) {
        this.hwViewLink = hwViewLink;
    }

    public int getHwId() {
        return hwId;
    }

    public void setHwId(int hwId) {
        this.hwId = hwId;
    }

    public ArrayList<HWFileModel> getHwFileModelList() {
        return hwFileModelList;
    }

    public void setHwFileModelList(ArrayList<HWFileModel> hwFileModelList) {
        this.hwFileModelList = hwFileModelList;
    }
}
