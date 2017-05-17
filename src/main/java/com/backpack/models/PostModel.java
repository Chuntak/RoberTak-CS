package com.backpack.models;
import java.sql.Timestamp;
import java.util.Date;
/**
 * PostModel - used to reflect data of a post
 * made by student or professor
 * Created by rvtru on 4/20/2017.
 */
public class PostModel {
    /* ID OF POST IN DB */
    private int id;
    /* ID OF PARENT POST (IF THIS IS A COMMENT) */
    private int parentId;
    /* ID OF THE AUTHOR IN DB */
    private int authorId;
    /* ID OF THE COURSE POST WAS MADE ON */
    private int crsId;
    /* FIRST NAME OF THE AUTHOR */
    private String firstName;
    /* LAST NAME OF THE AUTHOR */
    private String lastName;
    /* HEADER FOR THE POST (LIKE A TITLE) */
    private String header;
    /* CONTENT OF THE POST */
    private String content;
    /* BOOLEAN IF AUTHOR WANTED TO STAY PRIVATE */
    private boolean anon;
    /* DATE POST WAS CREATED */
    private Date dateCreated;
    /* NUMBER OF COMMENTS A POST HAS */
    private int commentCount;
    /* NUMBER OF LIKES A POST HAS */
    private int likes;
    /* PERMISSION FOR USER TO EDIT POST - ONLY IF PROF OR COURSE OR AUTHOR */
    private boolean editable;
    /* FLAG IF USER ALREADY LIKED A POST */
    private int liked;

    public PostModel(){
        id=0;
        authorId=0;
        firstName="";
        lastName="";
        header="";
        content="";
        anon=false;
        dateCreated=new Date(1000000);
        commentCount=0;
        likes=0;
        crsId=0;
        parentId=0;
        editable = false;
        liked = -1;
    }

    /* GETTERS & SETTERS */
    public int getLiked(){
        return liked;
    }

    public void setLiked(int liked){
        this.liked = liked;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isAnon() {
        return anon;
    }

    public void setAnon(boolean anon) {
        this.anon = anon;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    /* SPECIAL SETTER FOR MAPPING FROM DB */
    public void setDate(Timestamp date){
        long l = date.getTime();
        Date d = new Date(l);
        this.dateCreated = d;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getCrsId() {
        return crsId;
    }

    public void setCrsId(int crsId) {
        this.crsId = crsId;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

}
