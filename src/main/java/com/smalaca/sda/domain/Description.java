package com.smalaca.sda.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Description {
    private String description;
    private String longDescription;

    Description(String description, String longDescription) {
        this.description = description;
        this.longDescription = longDescription;
    }

    private Description() {}

    @Override
    public String toString() {
        return "Description{" +
                "description='" + description + '\'' +
                ", longDescription='" + longDescription + '\'' +
                '}';
    }
}
