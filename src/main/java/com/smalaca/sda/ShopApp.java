package com.smalaca.sda;

import com.mongodb.client.MongoCollection;
import com.smalaca.sda.mongodb.MongoClientConnectivity;
import com.smalaca.sda.mongodb.MongoCollections;
import org.bson.Document;

public class ShopApp {

    public static void main( String[] args ) {
        MongoClientConnectivity mongoClientConnectivity = new MongoClientConnectivity();
        mongoClientConnectivity.open();

        MongoCollection<Document> books = new MongoCollections(mongoClientConnectivity).getCollection("books");


        mongoClientConnectivity.close();
    }
}
