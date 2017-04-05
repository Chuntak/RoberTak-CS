package com.robertakcs.service;

import com.google.appengine.api.datastore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Chuntak on 2/12/2017.
 */
@Service
public class RoberTakCSServiceImplementation implements RoberTakCSServiceInterface {
    DatastoreService datastoreService;
    @Autowired
    public RoberTakCSServiceImplementation(DatastoreServiceFactoryInterface datastoreServiceFactory) {
        this.datastoreService = datastoreServiceFactory.getDatastoreService();
    }

}
