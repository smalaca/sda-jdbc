package com.smalaca.sda;

import com.mongodb.client.MongoCollection;
import com.smalaca.sda.domain.BookFactory;
import com.smalaca.sda.mongodb.MongoClientConnectivity;
import com.smalaca.sda.mongodb.MongoCollections;
import org.bson.Document;

import java.util.Arrays;

public class ShopApp {

    public static void main( String[] args ) {
        MongoClientConnectivity mongoClientConnectivity = new MongoClientConnectivity();
        mongoClientConnectivity.open();

        MongoCollection<Document> books = new MongoCollections(mongoClientConnectivity).getCollection("books");

        BookFactory bookFactory = new BookFactory();

        books.insertMany(Arrays.asList(
                bookFactory.create(
                        "Zbrodnia i Kara",
                        "Fiodor Dostojewski",
                        Arrays.asList("code quality", "design")
                ),
                bookFactory.create(
                        "JUnit",
                        "Ktoś mądry",
                        Arrays.asList("code quality", "software development")
                ),
                bookFactory.create(
                        "Kubuś Puchatek",
                        "Christopher XXX",
                        Arrays.asList("software development")
                )
        ));

        for (Document existingBook : books.find()) {
            System.out.println(existingBook);
        }

        mongoClientConnectivity.close();
    }
}
