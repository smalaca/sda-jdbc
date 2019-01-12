package com.smalaca.sda.domain;

import org.bson.Document;

import java.util.List;

public class BookFactory {
    public Document create(String title, String author, List<String> categories) {
        return new Document()
                .append("title", title)
                .append("author", author)
                .append("categories", categories)
                ;
    }
}
