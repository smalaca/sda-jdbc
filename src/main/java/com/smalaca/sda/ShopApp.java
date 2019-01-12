package com.smalaca.sda;

import com.mongodb.client.MongoCollection;
import com.smalaca.sda.mongodb.MongoClientConnectivity;
import com.smalaca.sda.mongodb.MongoCollections;
import org.bson.Document;

import java.util.Arrays;
import java.util.List;

import static java.time.LocalTime.now;

public class ShopApp {

    public static void main( String[] args ) {
        MongoClientConnectivity mongoClientConnectivity = new MongoClientConnectivity();
        mongoClientConnectivity.open();

        MongoCollections collections = new MongoCollections(mongoClientConnectivity);

//        collections.createCollection("membershipCard");

        MongoCollection<Document> membershipCard = collections.getCollection("membershipCard");

        membershipCard.insertMany(Arrays.asList(
                aMemebershipCard(
                        "Sebastian", "Malaca", Arrays.asList("id1", "id2", "id3", "id4"), "13"),
                aMemebershipCard(
                        "Otto", "Octabvius", Arrays.asList("id1", "id3", "id4"), "2")
                        .append("vip", true),
                aMemebershipCard(
                        "Harry", "Osborn", Arrays.asList("id2", "id3", "id4"), "3")
                        .append("payment", 69.13)
                        .append("vip", true),
                aMemebershipCard(
                        "Norman", "Osborn", Arrays.asList(), "123")
                        .append("payment", 691.3)
                        .append("vip", false),
                aMemebershipCard(
                        "May", "Parker", Arrays.asList("id1", "id2", "id3"), "2"),
                aMemebershipCard(
                        "Ben", "Parker", Arrays.asList("id3", "id4"), "98"),
                aMemebershipCard(
                        "Peter", "Parker", Arrays.asList("id1", "id2"), "1322"),
                aMemebershipCard(
                        "Mary Jane", "Watson", Arrays.asList("id1", "id3", "id4"), "767"),
                aMemebershipCard(
                        "Gwen", "Stacy", Arrays.asList("id1", "id2"), "79")
                        .append("payment", 1000)
                        .append("vip", true)
        ));

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
