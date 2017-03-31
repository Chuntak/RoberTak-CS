package com.robertakcs.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chuntak on 2/13/2017.
 */
public class ToDoListArray {
    private List<ToDoList> i;
    public ToDoListArray() {
        i = new ArrayList<ToDoList>();
    }

    public List<ToDoList> getItemArray() {
        return i;
    }

    public void setItemArray(List<ToDoList> iL){
        i = iL;
    }
}
