package com.robertakcs.models;


/**
 * Created by Chuntak on 3/30/2017.
 */
public class ToDoListModel {
    private int id;
    private boolean priv;
    private String email;
    private String listName;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public boolean getPrivate() {
        return priv;
    }
    public void setPrivate(boolean priv) {
        this.priv = priv;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getListName() {
        return listName;
    }
    public void setListName(String listName) {
        this.listName = listName;
    }
}
