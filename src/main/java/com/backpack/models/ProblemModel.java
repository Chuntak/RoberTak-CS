package com.backpack.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.util.ArrayList;

/**
 * ProblemModel - reflects the data of a question or problem
 * Created by Chuntak on 5/5/2017.
 */
public class ProblemModel {
    /*ID OF THE PROBLEM MODEL IN DATABASE */
    private int problemId;
    /*THE QUIZ ID THAT THE PROBLEM IN*/
    private int quizId;
    /*THE TYPE OF THE PROBLEM, EITHER MULTIPLE CHOICE OR SHORT ANSWER*/
    private String type;
    /*THE ACTUAL QUESTION QUERY*/
    private String question;
    /*THE ANSWER TO THE PROBLEM*/
    private String answer;
    /*THE TITLE OF THE PROBLEM -- CURRENTLY NOT USED*/
    private String title;
    /*MARKER IF THE PROBLEM IS DELETED, WHEN THE FRONT END SENDS IT BACK*/
    private boolean deleted;
    /*HOW MANY IS THIS PROBLEM WORTH*/
    private double pointsWorth;
    /*THE MULTIPLE CHOICE IF THIS QUESTION IS A MULTIPLE CHOICE*/
    private ArrayList<ChoiceModel> choices;


    /*GETTERS AND SETTERS*/
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getProblemId() {
        return problemId;
    }

    public void setProblemId(int problemId) {
        this.problemId = problemId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public double getPointsWorth() {
        return pointsWorth;
    }

    public void setPointsWorth(double pointsWorth) {
        this.pointsWorth = pointsWorth;
    }

    public ArrayList<ChoiceModel> getChoices() {
        return choices;
    }

    /*READS IN THE LIST FROM THE DATABASE*/
    public void setChoicesList(ArrayList<ChoiceModel> cml) { this.choices = cml; }

    /*FROM THE FRONTEND JAVASCRIPT, WE TAKE IN A JSON BECAUSE SPRING CANNOT SOMEHOW READ IN A LIST OF A LIST THAT CONTAINS A MODEL*/
    public void setChoices(String choicesJsonString) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.choices = mapper.readValue(choicesJsonString, TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, ChoiceModel.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
