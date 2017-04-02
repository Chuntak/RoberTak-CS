package com.robertakcs.service;

import com.google.appengine.api.datastore.Entity;
import com.robertakcs.domain.Item;
import com.robertakcs.domain.ToDoList;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Chuntak on 2/12/2017.
 */
public interface RoberTakCSServiceInterface {
    public Entity saveItemEntity(final long listId, final String category, final String description,
                                  final Date startDate, final Date endDate, final boolean completed, final int positionsInList, final long id);
    public Entity saveDoListEntity(final String email, final boolean priv, final String listName, final long iD);
    public ArrayList<ToDoList> getToDoListArrayEntity(ToDoList tDL);
    public ArrayList<ToDoList> getToDoListArrayByEmail(ToDoList tDL); //TODO
    public ArrayList<ToDoList> getViewableToDoListArray(ToDoList tDL);
    public ArrayList<Entity> getItemByListID(Item i);
    public Entity updateDoListEntity(Entity entity);
    public Entity updateItemEntity(Entity entity);


    public boolean deleteItemEntity(Entity entity);
    public boolean updateAllDoListEntity(ArrayList<ToDoList> toDoListList);
    public boolean updateAllItemEntity(ArrayList<Item> itemList);
//    public ToDoList getToDoListEntity(ToDoList tDL);
}
