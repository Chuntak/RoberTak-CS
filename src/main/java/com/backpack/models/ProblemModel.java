package com.backpack.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Chuntak on 5/5/2017.
 */
public class ProblemModel {
    private int problemId;
    private int quizId;
    private String type;
    private String question;
    private String answer;
    private String title;
    private boolean deleted;

    private ArrayList<ChoiceModel> choices;

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

    public ArrayList<ChoiceModel> getChoices() {
        return choices;
    }

    public void setChoices(String choicesJsonString) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.choices = mapper.readValue(choicesJsonString, TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, ChoiceModel.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
