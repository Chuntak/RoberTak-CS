package com.backpack.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * ChoiceModel - reflects a choice of a multiple choice problem
 * Created by Chuntak on 5/12/2017.
 */

@JsonIgnoreProperties(ignoreUnknown=true)
public class ChoiceModel {

    /*THE ID OF THE CHOICE*/
    private int id;
    /* THE ID OF THE QUESTION THAT THIS CHOICE IS USED*/
    private int questionId;
    /*THIS IS THE ANSWER CHOICE'S ANSWER */
    private String answerChoice;
    /*THE LETTER OF THE ANSWER SHOWN IN THE FRONT END -- CURRENTLY NOT USED*/
    private String answerLetter;


    /* GETTERS AND SETTERS */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getAnswerChoice() {
        return answerChoice;
    }

    public void setAnswerChoice(String answerChoice) {
        this.answerChoice = answerChoice;
    }

    public String getAnswerLetter() {
        return answerLetter;
    }

    public void setAnswerLetter(String answerLetter) {
        this.answerLetter = answerLetter;
    }

}
