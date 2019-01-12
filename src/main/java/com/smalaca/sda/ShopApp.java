package com.smalaca.sda;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.smalaca.sda.mongodb.MongoClientConnectivity;
import com.smalaca.sda.mongodb.MongoCollections;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Arrays;

public class ShopApp {

    public static void main( String[] args ) {
        MongoClientConnectivity mongoClientConnectivity = new MongoClientConnectivity();
        mongoClientConnectivity.open();

        MongoCollection<Document> books = new MongoCollections(mongoClientConnectivity).getCollection("books");

        Bson criteria = Filters.in("categories", Arrays.asList("software development"));

        FindIterable<Document> documents = books
                .find(criteria)
                .sort(Sorts.orderBy(
                        Sorts.ascending("author"),
                        Sorts.ascending("title"))
                )
                .projection(
                        Projections.fields(
                                Projections.exclude("author", "title")));

        for (Document document : documents) {
            System.out.println(document);
        }

        System.out.println("----------------------------------");
        System.out.println("----------------------------------");

        for (Document existingBook : books.find()) {
            System.out.println(existingBook);
        }

        mongoClientConnectivity.close();
    }
}
