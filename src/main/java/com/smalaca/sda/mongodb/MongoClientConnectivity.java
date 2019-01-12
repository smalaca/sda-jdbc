package com.smalaca.sda.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class MongoClientConnectivity {
    private MongoClient mongoClient;

    public void open() {
        mongoClient = new MongoClient(
                new ServerAddress("localhost", 27017),
                MongoCredential.createCredential("javakrk", "library", "javakrk".toCharArray()),
                MongoClientOptions.builder().build()
        );
    }

    public void close() {
        if (mongoClient == null) {
            throw new NoMongoDbConnectionEstablished();
        }

        mongoClient.close();
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }
}
