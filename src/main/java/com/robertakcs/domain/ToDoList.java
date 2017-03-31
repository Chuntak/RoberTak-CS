package com.robertakcs.domain;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

/**
 * Created by Chuntak on 2/12/2017.
 */
public class ToDoList {

    public static final String EMAIL = "email";
    public static final String PRIVATE = "private";
    public static final String LIST_NAME = "listName";
    private static final String ID = "real_ID";

    public static final String TO_DO_LIST_ENTITY     = "Todolist";

    /*HAS A UNIQUE ID*/
    private Entity entity = new Entity(TO_DO_LIST_ENTITY);

    public ToDoList(){}
    public ToDoList(Entity e){ entity = e; entity.setProperty(ID, entity.getKey().getId()); }
    // For created new nodo
    public ToDoList(final String email, final boolean priv, final String listName, final long iD){
        entity.setProperty(EMAIL, email);
        entity.setProperty(PRIVATE, priv);
        entity.setProperty(LIST_NAME, listName);
    }

    public void setEntity(Entity e) { entity = e; }
    public String getEmail() {      return (String) entity.getProperty(EMAIL); }
    public boolean getPrivate() { return (Boolean) entity.getProperty(PRIVATE); }
    public String getListName() { return (String) entity.getProperty(LIST_NAME); }
    public long getID() { return (Long) entity.getProperty(ID); }
    public void setEmail(String email) { entity.setProperty(EMAIL, email); }
    public void setPrivate(boolean priv) { entity.setProperty(PRIVATE, priv); }
    public void setListName(String listName) { entity.setProperty(LIST_NAME, listName);}
    public void setID(long id) { entity.setProperty(ID, id); }
    public Entity getEntity() {  return entity; }
}
