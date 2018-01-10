package com.andersen.test_ufm.repository;

import com.mongodb.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.net.UnknownHostException;
import java.util.List;

@Repository
public class ClientRepository {

    @Value("${mongodb.database.name:test}")
    private String mongodbDatabaseName;

    @Value("${mongodb.collection.name:clients}")
    private String mongodbCollectionName;

    private MongoClient mongo;
    private DB mongoDB;
    private DBCollection mongoCollection = null;

    @PostConstruct
    public void initConnection() {
        try {
            mongo = new MongoClient("localhost", 27017);
            mongoDB = mongo.getDB(mongodbDatabaseName);
            mongoCollection = mongoDB.getCollection(mongodbCollectionName);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public List getAllClients() {
        if (mongoCollection == null) {
            this.initConnection();
        }
        return mongoCollection.find().toArray();
    }

    public DBObject createClient(String clientId, String spentTotal, Boolean isBig) {
        DBObject newUser = new BasicDBObject();
        newUser.put("clientId", clientId);
        newUser.put("spentTotal", spentTotal);
        newUser.put("isBig", isBig);
        if (mongoCollection == null) {
            this.initConnection();
        }
        mongoCollection.save(newUser);
        return mongoCollection.findOne(newUser);
    }

    public DBObject findClientByClientId(String clientId) {
        if (mongoCollection == null) {
            this.initConnection();
        }
        return mongoCollection.findOne(new BasicDBObject("clientId", clientId));
    }

}
