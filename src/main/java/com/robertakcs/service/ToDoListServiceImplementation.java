package com.robertakcs.service;

import com.google.appengine.api.datastore.*;
import com.robertakcs.domain.Item;
import com.robertakcs.domain.ToDoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

import static com.robertakcs.domain.ToDoList.*;

/**
 * Created by Chuntak on 2/12/2017.
 */
@Service
public class ToDoListServiceImplementation implements ToDoListServiceInterface {
    DatastoreService datastoreService;
    @Autowired
    public ToDoListServiceImplementation(DatastoreServiceFactoryInterface datastoreServiceFactory) {
        this.datastoreService = datastoreServiceFactory.getDatastoreService();
    }

    public Entity saveItemEntity(final long listId, final String category, final String description,
                                  final Date startDate, final Date endDate, final boolean completed, final int positionInList, final long id) {
        Item it = new Item(listId, category, description, startDate, endDate, completed, positionInList, id);
        Entity itEntity = it.getEntity();
        SaveTransactions(itEntity);
        return itEntity;
    }

    public Entity saveDoListEntity(final String email, final boolean priv, final String listName, final long id){
        ToDoList tdl = new ToDoList(email, priv, listName, id);
        Entity toDoEntity = tdl.getEntity();
        SaveTransactions(toDoEntity);
        return toDoEntity;
    }

    public Entity updateDoListEntity(Entity entity) {
        // ToDoList tdl = new ToDoList( (String) entity.getProperty(EMAIL),(Boolean) entity.getProperty(PRIVATE),(String) entity.getProperty(LIST_NAME));
        Entity ent = getEntityByID(entity, TO_DO_LIST_ENTITY);
        if(ent != null) {
            if (entity.hasProperty(EMAIL)) ent.setProperty(EMAIL, entity.getProperty(EMAIL));
            if (entity.hasProperty(PRIVATE)) ent.setProperty(PRIVATE, entity.getProperty(PRIVATE));
            if (entity.hasProperty(LIST_NAME)) ent.setProperty(LIST_NAME, entity.getProperty(LIST_NAME));
            SaveTransactions(ent);
        }
        return ent;
    }

    public Entity updateItemEntity(Entity entity){
        Entity ent = getEntityByID(entity, Item.ITEM_ENTITY);
        if(ent != null) {
//            if (entity.hasProperty(LIST_ID)) ent.setProperty(LIST_ID, entity.getProperty(LIST_ID));
            if (entity.hasProperty(Item.CATEGORY)) ent.setProperty(Item.CATEGORY, entity.getProperty(Item.CATEGORY));
            if (entity.hasProperty(Item.DESCRIPTION)) ent.setProperty(Item.DESCRIPTION, entity.getProperty(Item.DESCRIPTION));
            if (entity.hasProperty(Item.START_DATE)) ent.setProperty(Item.START_DATE, entity.getProperty(Item.START_DATE));
            if (entity.hasProperty(Item.END_DATE)) ent.setProperty(Item.END_DATE, entity.getProperty(Item.END_DATE));
            if (entity.hasProperty(Item.COMPLETED)) ent.setProperty(Item.COMPLETED, entity.getProperty(Item.COMPLETED));
            if (entity.hasProperty(Item.POSITION_IN_LIST)) ent.setProperty(Item.POSITION_IN_LIST, entity.getProperty(Item.POSITION_IN_LIST));
            SaveTransactions(ent);
        }
        return ent;
    }

    private Entity getEntityByID(Entity entity, String kind){
        Query query = new Query(kind);
        Entity ent = null;
        for (Entity e : datastoreService.prepare(query).asIterable()){
            if(((Long)e.getProperty(Item.ID)).equals((Long)entity.getProperty(Item.ID))){
                ent = e;
                break;
            }
        }
        return ent;
    }

    private boolean SaveTransactions(Entity entity){
        saveTransactions(entity);
        entity.setProperty(Item.ID,entity.getKey().getId());
        return saveTransactions(entity);
    }

    private boolean saveTransactions(Entity entity){
        boolean saved = false;
        Transaction transaction = datastoreService.beginTransaction();
        try {
            // save entity in dataStore
            Key key = datastoreService.put(transaction, entity);
            transaction.commit();
            saved = true;
        } catch(Exception e) {
            saved = false;
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                saved = false;
            }
        }
        return saved;
    }

    @Override
    public ArrayList<ToDoList> getToDoListArrayEntity(ToDoList tDL) {
        ArrayList<ToDoList> tDLArray = new ArrayList<ToDoList>();
        Query query = new Query(TO_DO_LIST_ENTITY);
        for (Entity entity : datastoreService.prepare(query).asIterable()) {
            ToDoList model = new ToDoList(entity);
            tDLArray.add(model);
        }
        return tDLArray;
    }

    public ArrayList<ToDoList> getToDoListArrayByEmail(ToDoList tDL) {
        ArrayList<ToDoList> filteredList = new ArrayList<ToDoList>();
        for(ToDoList tdl : getToDoListArrayEntity(tDL)){
            if(tdl.getEmail().equals(tDL.getEmail())){
                filteredList.add(tdl);
            }
        }
        return filteredList;
    }

    public ArrayList<ToDoList> getViewableToDoListArray(ToDoList tDL) {
        ArrayList<ToDoList> filteredList = new ArrayList<ToDoList>();
        for(ToDoList tdl : getToDoListArrayEntity(tDL)){
            if (tdl.getPrivate() == false && !tdl.getEmail().equals(tDL.getEmail())){
                filteredList.add(tdl);
            }
        }
        return filteredList;
    }

    public ArrayList<Entity> getItemByListID(Item i) {
        ArrayList<Entity> itemArray = new ArrayList<Entity>();
        Query query = new Query(Item.ITEM_ENTITY);
        for(Entity entity : datastoreService.prepare(query).asIterable()) {
            if(((Long)entity.getProperty(Item.LIST_ID)).equals(i.getListId())){
                itemArray.add(entity);
            }
        }
        //   itemArray.add(new Item());
        return itemArray;
    }

    public boolean deleteItemEntity(Entity entity) {
        Entity ent = getEntityByID(entity, Item.ITEM_ENTITY);
        boolean deleted = false;
        Transaction transaction = datastoreService.beginTransaction();
        try {
            // save entity in dataStore
            datastoreService.delete(ent.getKey());
            transaction.commit();
            deleted = true;
        } catch(Exception e) {
            deleted = false;
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                deleted = false;
            }
        }
        return deleted;
    }

    private boolean entityExist(Entity entity){
        Query query = new Query(entity.getKey().getKind());
        for (Entity e : datastoreService.prepare(query).asIterable()) {
            if(((Long)e.getProperty(Item.ID)).equals((Long)entity.getProperty(Item.ID)))
                return true;
        }
        return false;
    }

    public boolean updateAllDoListEntity(ArrayList<ToDoList> toDoListList){
        for(ToDoList tdl : toDoListList){
            if(entityExist(tdl.getEntity())){
                updateDoListEntity(tdl.getEntity());
            } else{
                saveDoListEntity(tdl.getEmail(),tdl.getPrivate(),tdl.getListName(), tdl.getID());
            }
        }
        return true;
    }

    public boolean updateAllItemEntity(ArrayList<Item> itemList){
        for(Item iL : itemList){
            if(entityExist(iL.getEntity())){
                updateItemEntity(iL.getEntity());
            } else {
                saveItemEntity(iL.getListId(),iL.getCategory(),iL.getDescription(),iL.getStartDate(),iL.getEndDate(),iL.getCompleted(), iL.getPositionInList(), iL.getID());
            }
        }
        return true;
    }

//    @Override
//    public ToDoList getToDoListEntity(ToDoList tDL) {
//        Query query = new Query(TO_DO_LIST_ENTITY);
//        Query.Filter keyFilter =
//                new Query.FilterPredicate(Entity.KEY_RESERVED_PROPERTY, Query.FilterOperator.EQUAL, tDL.getEntity().getKey());
//        query.setFilter(keyFilter);
//        ToDoList model = new ToDoList(datastoreService.prepare(query).asSingleEntity());
//        return model;
//    }


}
