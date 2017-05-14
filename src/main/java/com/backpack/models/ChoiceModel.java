package com.backpack.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Chuntak on 5/12/2017.
 */

@JsonIgnoreProperties(ignoreUnknown=true)
public class ChoiceModel {
    private int id;
    private int questionId;
    private String answerChoice;
    private String answerLetter;

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
