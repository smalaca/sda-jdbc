package com.smalaca.sda;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Projections;
import com.smalaca.sda.mongodb.MongoClientConnectivity;
import com.smalaca.sda.mongodb.MongoCollections;
import org.bson.Document;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.time.LocalTime.now;

public class ShopApp {

    public static void main( String[] args ) {
        MongoClientConnectivity mongoClientConnectivity = new MongoClientConnectivity();
        mongoClientConnectivity.open();

        MongoCollections collections = new MongoCollections(mongoClientConnectivity);

        MongoCollection<Document> membershipCard = collections.getCollection("membershipCard");
        MongoCollection<Document> books = collections.getCollection("books");

        Set<String> fakeIds = new HashSet<>();

        FindIterable<Document> cards = membershipCard
                .find()
                .projection(Projections.fields(Projections.include("books"), Projections.exclude("_id")));

        for (Document existingBook : cards) {
            List<String> ids = (List<String>) existingBook.get("books");
            fakeIds.addAll(ids);
        }

        Map<String, String> booksId = new HashMap<>();

        FindIterable<Document> documents = books
                .find()
                .limit(fakeIds.size())
                .projection(
                    Projections.fields(Projections.include("_id"))
                );

        Iterator<String> iterator = fakeIds.iterator();
        for (Document document : documents) {
            booksId.put(iterator.next(), document.get("_id").toString());
        }

        System.out.println(booksId);

        System.out.println("---------------------");

        for (Document existingBook : membershipCard.find()) {
            System.out.println(existingBook);
        }

        mongoClientConnectivity.close();
    }

    private static Document aMemebershipCard(String name, String lastName, List<String> books, String number) {
        return new Document()
                .append("person", new Document()
                        .append("firstName", name)
                        .append("lastName", lastName)
                )
                .append("books", books)
                .append("creationDate", now().toString())
                .append("number", number);
    }
}
