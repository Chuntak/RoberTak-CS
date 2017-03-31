package com.robertakcs.databaseConnection;

import com.robertakcs.models.ToDoListModel;
import com.robertakcs.models.ItemModel;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Created by Chuntak on 3/30/2017.
 * THIS IS NOT USED
 */
public class HibernateUtil {
    private static SessionFactory sessionFactory ;
    static {
        String url = "";
        String connectionDriver = "";
        if (System.getProperty("com.google.appengine.runtime.version").startsWith("Google App Engine/")) {
            url = System.getProperty("ae-cloudsql.cloudsql-database-url");
            // Load the class that provides the new "jdbc:google:mysql://" prefix.
            connectionDriver = "com.mysql.jdbc.GoogleDriver";
        } else {
            // Set the url with the local MySQL database connection url when running locally
            url = System.getProperty("ae-cloudsql.local-database-url");
            connectionDriver = "com.mysql.jdbc.Driver";
        }
        url = "127.0.0.1:3306";
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(ItemModel.class);
        configuration.addAnnotatedClass(ToDoListModel.class);
        configuration.setProperty("hibernate.connection.driver_class",connectionDriver);
        configuration.setProperty("hibernate.connection.url", url);
//        configuration.setProperty("hibernate.connection.datasource", "RedRobins");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "RedRobins");
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.connection.pool_size", "10");

        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
    }
    public static SessionFactory getSessionFactory() {
        //return null;
        return sessionFactory;
    }
}
