package com.backpack.models;

import org.springframework.web.multipart.MultipartFile;


import java.util.Date;
import java.util.ArrayList;


/**
 * AssignmentModel - used to reflect an
 * assignment created by the professor.
 * Also a gradable item.
 * Created by rvtru on 4/5/2017.
 */
public class AssignmentModel {

    /* ID IN DB */
    private int id;
    /* ID OF COURSE THAT THIS ASSIGNMENT BELONGS TO */
    private int courseId;
    /* TITLE OF THE ASSIGNMENT */
    private String title;
    /* DESCRIPTION OF ASSIGNMENT */
    private String description;
    /* THE KIND OF GRADABLE ITEM THE OBJECT IS (hw) */
    private String gradableType;
    /* MAX POSSIBLE GRADE FOR ASSIGNMENT */
    private double maxGrade;
    /* DUE DATE OF ASSIGNMENT */
    private Date dueDate;
    /* DIFFICULTY OF ASSIGNMENT */
    private String difficulty;
    /* IS THE ASSIGNMENT SUBMITTABLE FOR STUDENTS */
    private boolean submittable;

    /* FILE OBJECT TO BE SAVED - EITHER ATTACHED FILE BY PROF OR STUDENT SUBMISSION */
    private MultipartFile hwFile;

    /* FILE INFO FOR ATTACHED ASSIGNMENT FILE */
    private String hwBlobName;
    private String hwFileName;
    private String hwDownloadLink;
    private String hwViewLink;
    /* ID FOR WHEN MULTIPLE FILES ARE SUPPORTED */
    private int hwId;

    /* FILE INFO FOR STUDENT SUBMSSIONS - ONLY USED FROM BACKEND -> FRONTEND */

    /* USE IT TO GET THE ID FOR BLOB, BLOB IS THE FILE */
    private String submissionBlobName;
    private String submissionDownloadLink;
    private String submissionFileName;
    private String submissionViewLink;

    /* @TODO FOR MULTIPLE FILE SUPPORT */
    private ArrayList<HWFileModel> hwFileModelList;


    /* DEFAULT CONSTRUCTOR NOT NEEDED */
    public AssignmentModel() {
    }

    /**
     * parameterized constructor - for mappings from Controllers
     * @param id
     * @param courseId
     * @param title
     * @param description
     * @param gradableType
     * @param maxGrade
     * @param dueDate
     * @param difficulty
     * @param submittable
     */
    public AssignmentModel(int id, int courseId, String title, String description, String gradableType, double maxGrade, Date dueDate,  String difficulty, boolean submittable) {
        this.id = id;
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.gradableType = gradableType;
        this.maxGrade = maxGrade;
        this.dueDate = dueDate;
        this.difficulty = difficulty;
        this.submittable = submittable;
    }

    /* GETTERS AND SETTERS */
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
    public void setDate(java.sql.Timestamp dueDate) {
        /* SEND DUE DATE TO CONTROLLER AS LONG */
        this.dueDate = new Date(dueDate.getTime());
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

    public boolean isSubmittable() {
        return submittable;
    }

    public void setSubmittable(boolean submittable) {
        this.submittable = submittable;
    }

    public String getSubmissionBlobName() {
        return submissionBlobName;
    }

    public void setSubmissionBlobName(String submissionBlobName) {
        this.submissionBlobName = submissionBlobName;
    }

    public String getSubmissionDownloadLink() {
        return submissionDownloadLink;
    }

    public void setSubmissionDownloadLink(String submissionDownloadLink) {
        this.submissionDownloadLink = submissionDownloadLink;
    }

    public String getSubmissionFileName() {
        return submissionFileName;
    }

    public void setSubmissionFileName(String submissionFileName) {
        this.submissionFileName = submissionFileName;
    }

    public String getSubmissionViewLink() {
        return submissionViewLink;
    }

    public void setSubmissionViewLink(String submissionViewLink) {
        this.submissionViewLink = submissionViewLink;
    }
}
