package com.robertakcs.dao;

import com.robertakcs.databaseConnection.DBSingleton;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Created by Chuntak on 3/30/2017.
 */
public class DAOBase {
    protected DBSingleton dbs;
    public DAOBase(){
        dbs = DBSingleton.getSingleton();
    }

    /*checks if the columnexists*/
    protected static boolean columnExists(ResultSet rs, String columnName){
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            for(int i = 1;i <= rsmd.getColumnCount(); i++){
                if(rsmd.getColumnName(i).equals(columnName))
                    return true;
            }
            return false;
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
