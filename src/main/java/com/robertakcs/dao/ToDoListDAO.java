package com.robertakcs.dao;

import com.robertakcs.models.ToDoListModel;

/**
 * Created by Chuntak on 2/28/2017.
 */
public class ToDoListDAO extends DAOBase {
    public boolean addToDoList(ToDoListModel list){
        String query = "INSERT INTO ToDoLists (private, email, listName) VALUES(?, ?, ?)";
        int numRowsAffected = dbs.getJdbcTemplate().update(query, list.getPrivate(), list.getEmail(), list.getListName()); /*calls SQL and returns the num row affected*/
        return numRowsAffected > 0 ? true : false; /* if > 0 then its consider success transaction */
    }
}
