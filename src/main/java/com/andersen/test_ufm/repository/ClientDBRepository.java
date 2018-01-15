package com.andersen.test_ufm.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.net.UnknownHostException;
import java.util.List;

@Repository
public class ClientDBRepository {

    private final DBCollection mongoCollection;

    @Autowired
    public ClientDBRepository(@Value("${mongodb.database.name:test}") String mongodbDatabaseName,
                              @Value("${mongodb.collection.name:clients}") String mongodbCollectionName,
                              @Value("${mongodb.host:localhost}") String localhost,
                              @Value("${mongodb.port:27017}") int port) throws UnknownHostException {
        this(new MongoClient(localhost, port).getDB(mongodbDatabaseName)
                .getCollection(mongodbCollectionName));
    }

    public ClientDBRepository(DBCollection mongoCollection) {
        this.mongoCollection = mongoCollection;
    }

    public List getAllClients() {
        return mongoCollection.find().toArray();
    }

    public DBObject createClient(String clientId, String spentTotal, Boolean isBig) {
        DBObject inputDbObject = new BasicDBObject();
        inputDbObject.put("clientId", clientId);
        inputDbObject.put("spentTotal", spentTotal);
        inputDbObject.put("isBig", isBig);
        return this.createClient(inputDbObject);
    }

    public DBObject createClient(JSONObject data) {
        DBObject inputDbObject = new BasicDBObject ();
        inputDbObject.putAll(data);
        return this.createClient(inputDbObject);
    }

    public DBObject createClient(DBObject data) {
        if (data == null) {
            return new BasicDBObject();
        }
        DBObject searchCriteria = new BasicDBObject("clientId", data.get("clientId"));
        DBObject existingDocument = mongoCollection.findOne(searchCriteria);
        if (existingDocument != null) {
            data.put("_id", existingDocument.get("_id"));
        }
        mongoCollection.save(data);
        return mongoCollection.findOne(searchCriteria);
    }

    public DBObject findClientByClientId(String clientId) {
        return mongoCollection.findOne(new BasicDBObject("clientId", clientId));
    }
}
