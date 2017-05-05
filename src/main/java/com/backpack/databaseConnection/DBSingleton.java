package com.backpack.databaseConnection;



import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.cloud.storage.*;
import com.google.cloud.storage.Blob;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.io.IOUtils;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;
import org.jasypt.salt.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.multipart.MultipartFile;


import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Properties;

/**
 * THIS IS A SINGLETON THAT ALLOWS YOU TO ACCESS DATABASE/OTHER DATASOURCE
 * @author Chuntak
 */
public class DBSingleton {

    private String DB_URL;
    private String DRIVER;
    private String USER;
    private String PASS;
    private String BUCKET_NAME;
    private static DBSingleton dbs;
    private JdbcTemplate jdbcTemplate;
    private Storage storage = null;

    private DBSingleton() throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        setDBProperties();
        jdbcTemplate = new JdbcTemplate(getDataSource());
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

    public Blob getBlob(String blobName){
       return storage.get(BlobId.of(BUCKET_NAME, blobName));
    }

    private void setDBProperties(){
        BUCKET_NAME = System.getProperty("bucketName");
        /*setup the encryptor*/
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setSaltGenerator(new ZeroSaltGenerator()); /*not using salt*/
        encryptor.setPassword(System.getProperty("encrpyPass"));

        /*Getting the class depending whether we are on web or local*/
        if (System.getProperty("com.google.appengine.runtime.version").startsWith("Google App Engine/")) {
            DB_URL = System.getProperty("ae-cloudsql.cloudsql-database-url"); /*THIS IS FROM appengine.web.xml*/
            // Load the class that provides the new "jdbc:google:mysql://" prefix.
            DRIVER = "com.mysql.jdbc.GoogleDriver";

            /*tries to grab file from google storage for password*/
            GcsFilename gcs_filename = new GcsFilename(BUCKET_NAME, System.getProperty("dbPropertyFile"));
            GcsService service = GcsServiceFactory.createGcsService();
            ReadableByteChannel rbc = null;
            try {
                rbc = service.openReadChannel(gcs_filename, 0);
                InputStream is = Channels.newInputStream(rbc);
                Properties props = new EncryptableProperties(encryptor);
                props.load(is);
                USER = props.getProperty("datasource.username");
                PASS = props.getProperty("datasource.password");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Set the url with the local MySQL database connection url when running locally
            DB_URL = System.getProperty("ae-cloudsql.local-database-url"); /*THIS IS FROM appengine.web.xml*/
            DRIVER = "com.mysql.jdbc.Driver";
            USER = System.getProperty("dbUser");
            PASS = encryptor.decrypt(System.getProperty("dbPass"));
        }
    }

    private DataSource getDataSource(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setMinIdle(10);
        dataSource.setMaxActive(50);
        dataSource.setMaxOpenPreparedStatements(100);
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUrl(DB_URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASS);
        return dataSource;
    }
}