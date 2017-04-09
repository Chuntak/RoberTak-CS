package com.backpack.databaseConnection;


import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.sql.*;

/**
 * THIS IS A SINGLETON THAT ALLOWS YOU TO ACCESS DATABASE/OTHER DATASOURCE
 * @author Chuntak
 */
public class DBSingleton {

    final String DB_URL;
    final String DRIVER;
    final String USER = "root";
    final String PASS = "RedRobins";
    final String SCHEMA = "RedRobins";
    private static DBSingleton dbs;
    private JdbcTemplate jdbcTemplate;

    private DBSingleton() throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        if (System.getProperty("com.google.appengine.runtime.version").startsWith("Google App Engine/")) {
            DB_URL = System.getProperty("ae-cloudsql.cloudsql-database-url"); /*THIS IS FROM appengine.web.xml*/
            // Load the class that provides the new "jdbc:google:mysql://" prefix.
            DRIVER = "com.mysql.jdbc.GoogleDriver";
        } else {
            // Set the url with the local MySQL database connection url when running locally
            DB_URL = System.getProperty("ae-cloudsql.local-database-url"); /*THIS IS FROM appengine.web.xml*/
            DRIVER = "com.mysql.jdbc.Driver";
        }
        /*Getting the class depending whether we are on web or local*/
        Class c = Class.forName(DRIVER);
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUrl(DB_URL + "/RedRobins");
        dataSource.setUsername(USER);
        dataSource.setPassword(PASS);
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /*RETURNS THE SINGLETON*/
    public static DBSingleton getSingleton() {
        if (dbs == null) {
            try {
                dbs = new DBSingleton();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dbs;
    }

    /*THE MEAT OF THIS SINGLETON*/
    public JdbcTemplate getJdbcTemplate(){
        return jdbcTemplate;
    }


}
