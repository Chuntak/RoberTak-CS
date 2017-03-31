package com.robertakcs.domain;

import com.google.appengine.api.datastore.Entity;
import java.util.Date;
/**
 * Created by Chuntak on 2/11/2017.
 */
public class Item {



    public static final String LIST_ID = "listId"; //ToDoListID
    public static final String CATEGORY = "category";
    public static final String DESCRIPTION = "description";
    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";
    public static final String COMPLETED = "completed";
    public static final String POSITION_IN_LIST = "positionInList";
    public static final String ID = "real_ID";

    public static final String ITEM_ENTITY     = "Item";

    /*THIS ASSIGNES A UNIT NUMERIC ID AND INITIALIZES AN ENTITY FOR ITEMS*/
    private Entity entity = new Entity(ITEM_ENTITY);
    public Item(){};

    public Item(Entity e) { entity = e; }

    public Item(final long listId, final String category, final String description,
                final Date startDate, final Date endDate, final boolean completed, final int positionInList, final long id){
        entity.setProperty(LIST_ID, listId);
        entity.setProperty(CATEGORY, category);
        entity.setProperty(DESCRIPTION, description);
        entity.setProperty(START_DATE, startDate);
        entity.setProperty(END_DATE, endDate);
        entity.setProperty(COMPLETED, completed);
        entity.setProperty(POSITION_IN_LIST, positionInList);
        entity.setProperty(ID, entity.getKey().getId());
    }

    public long getListId() { return (Long) entity.getProperty(LIST_ID);}
    public String getCategory() { return (String) entity.getProperty(CATEGORY); }
    public String getDescription() { return (String) entity.getProperty(DESCRIPTION); }
    public Date getStartDate() { return (Date) entity.getProperty(START_DATE); }
    public Date getEndDate() { return (Date) entity.getProperty(END_DATE); }
    public boolean getCompleted() { return (Boolean) entity.getProperty(COMPLETED); }
    public int getPositionInList() { return (Integer) entity.getProperty(POSITION_IN_LIST); }
    public long getID() { return (Long) entity.getProperty(ID); }

    public void setListId(long listId) { entity.setProperty(LIST_ID, listId); }
    public void setCategory(String category) {  entity.setProperty(CATEGORY, category); }
    public void setDescription(String description) { entity.setProperty(DESCRIPTION, description);  }
    public void setStartDate(Date startDate) { entity.setProperty(START_DATE, startDate); }
    public void setEndDate(Date endDate) {  entity.setProperty(END_DATE, endDate); }
    public void setCompleted(boolean completed) { entity.setProperty(COMPLETED, completed); }
    public void setPositionInList(int positionInList) { entity.setProperty(POSITION_IN_LIST, positionInList); }
    public void setID(long id) { entity.setProperty(ID, id); }

    public Entity getEntity() {
        return entity;
    }
}
