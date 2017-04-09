package com.backpack.service;

import com.google.appengine.api.datastore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Chuntak on 2/12/2017.
 */
@Service
public class BackpackServiceImplementation implements BackpackServiceInterface {
    DatastoreService datastoreService;
    @Autowired
    public BackpackServiceImplementation(DatastoreServiceFactoryInterface datastoreServiceFactory) {
        this.datastoreService = datastoreServiceFactory.getDatastoreService();
    }

}
