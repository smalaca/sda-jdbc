package com.smalaca.sda.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;

public class MongoCollections {
    private final MongoClient mongoClient;

    public MongoCollections(MongoClientConnectivity mongoClientConnectivity) {
        mongoClient = mongoClientConnectivity.getMongoClient();
    }

    public MongoCollection<Document> getCollection(String name) {
        MongoDatabase library = mongoClient.getDatabase("library");
        return library.getCollection(name);
    }

    public void createCollection(String name) {
        mongoClient.getDatabase("library").createCollection(name);
    }

    public MongoIterable<String> collections() {
        return mongoClient.getDatabase("library").listCollectionNames();
    }
}
