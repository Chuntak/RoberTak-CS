package com.robertakcs.dao;

import com.robertakcs.databaseConnection.DBSingleton;

/**
 * Created by Chuntak on 3/30/2017.
 */
public class DAOBase {
    protected DBSingleton dbs;
    public DAOBase(){
        dbs = DBSingleton.getSingleton();
    }


}
