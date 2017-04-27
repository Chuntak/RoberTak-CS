package com.backpack.databaseConnection;



import com.google.cloud.storage.*;
import com.google.cloud.storage.Blob;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

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

    private static final String BUCKET_NAME = "backpack-164101.appspot.com";
    private static Storage storage = null;

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
        storage = StorageOptions.getDefaultInstance().getService();
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

    public Blob uploadFile(MultipartFile file) {
        List<Acl> acls = new ArrayList<>();
        acls.add(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
        String fileName = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "|" +  file.getOriginalFilename();
        try {
            Blob blob = storage.create(BlobInfo.newBuilder(BUCKET_NAME, fileName).setAcl(acls).build(),
                    file.getInputStream());
            return blob;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteFile(String blobName){
        return storage.delete(BlobId.of(BUCKET_NAME, blobName));
    }

    /*creates a link to view file on webpage*/
    public String getFileViewLink(String blobName, boolean embedded) {
        return "https://docs.google.com/gview?url=https://storage.googleapis.com/" + BUCKET_NAME + "/" + blobName +
                (embedded ? "&embedded=true" : "");
    }

    public BlobInfo getBlobInfo(String blobName){
        BlobInfo b = Blob.newBuilder(BUCKET_NAME, blobName).build();
        return b;
    }
}