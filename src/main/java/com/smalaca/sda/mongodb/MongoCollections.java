package com.smalaca.sda.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;

public class MongoCollections {
    private final MongoClient mongoClient;
    private MongoDatabase database;

    public MongoCollections(MongoClientConnectivity mongoClientConnectivity) {
        mongoClient = mongoClientConnectivity.getMongoClient();
    }

    public MongoCollection<Document> getCollection(String name) {
        return aDatabase().getCollection(name);
    }

    public void createCollection(String name) {
        aDatabase().createCollection(name);
    }

    public MongoIterable<String> collections() {
        return aDatabase().listCollectionNames();
    }

    private MongoDatabase aDatabase() {
        if (database == null) {
            database = mongoClient.getDatabase("library");
        }

        return database;
    }
}
