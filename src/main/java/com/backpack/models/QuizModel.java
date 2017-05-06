package com.backpack.models;


import java.util.ArrayList;
import java.util.Date;

/**
 * Created by CHUNTAK on 5/5/2017.
 */
public class QuizModel{
    /* ID OF QUIZ IN DB */
    private int id;
    /* ID OF COURSE THE QUIZ BELONGS TO */
    private int courseId;
    /* TITLE OF THE ANNOUNCEMENT */
    private String title;
    /* DATE THE QUIZ IS DUE*/
    private Date dueDate;

    /* LIST OF PROBLEMS*/
    private ArrayList<ProblemModel> problems;


    /* THE MAX GRADE FOR THE QUIZ*/
    private double maxGrade;

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

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }


    public double getMaxGrade() {
        return maxGrade;
    }

    public void setMaxGrade(double maxGrade) {
        this.maxGrade = maxGrade;
    }

    public ArrayList<ProblemModel> getProblems() {
        return problems;
    }

    public void setProblems(ArrayList<ProblemModel> problems) {
        this.problems = problems;
    }

}
