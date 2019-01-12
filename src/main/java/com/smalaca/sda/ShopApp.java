package com.smalaca.sda;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.smalaca.sda.mongodb.MongoClientConnectivity;
import com.smalaca.sda.mongodb.MongoCollections;
import org.bson.Document;

public class ShopApp {

    public static void main( String[] args ) {
        MongoClientConnectivity mongoClientConnectivity = new MongoClientConnectivity();
        mongoClientConnectivity.open();

        MongoCollection<Document> books = new MongoCollections(mongoClientConnectivity).getCollection("books");

        books.updateMany(
                Filters.eq("description", "was great"),
                Updates.combine(
                        Updates.set("publish year", 2000),
                        Updates.addToSet("categories", "anthem")
                )
        );

        System.out.println("----------------------------------");
        System.out.println("----------------------------------");

        for (Document existingBook : books.find()) {
            System.out.println(existingBook);
        }

        mongoClientConnectivity.close();
    }
}
