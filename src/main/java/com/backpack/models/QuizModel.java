package com.backpack.models;

import java.util.Date;

/**
 * Created by Admin on 5/5/2017.
 */
public class QuizModel {
    /* ID OF ANNOUNCEMENT IN DB */
    private int id;
    /* ID OF COURSE THE ANNOUNCEMENT BELONGS TO */
    private int courseId;
    /* TITLE OF THE ANNOUNCEMENT */
    private String title;
    /* CONTENT OF THE ANNOUNCEMENT */
    private String description;
    /* DATE THE ANNOUNCEMENT WAS CREATED */
    private Date dateCreated;
}
