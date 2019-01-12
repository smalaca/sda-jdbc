package com.smalaca.sda;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.smalaca.sda.mongodb.MongoClientConnectivity;
import org.bson.Document;

public class ShopApp {
    public static void main( String[] args ) {
        MongoClientConnectivity mongoClientConnectivity = new MongoClientConnectivity();
        mongoClientConnectivity.open();

        MongoClient mongoClient = mongoClientConnectivity.getMongoClient();
        MongoDatabase library = mongoClient.getDatabase("library");

        library.createCollection("books");

        for (String name : library.listCollectionNames()) {
            System.out.println(name);
        }

        MongoCollection<Document> books = library.getCollection("books");


        mongoClientConnectivity.close();
    }
}
