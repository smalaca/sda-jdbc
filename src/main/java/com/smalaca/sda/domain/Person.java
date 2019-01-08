package com.smalaca.sda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Person {
    @Id
    @GeneratedValue
    @Column(name = "person_id")
    private int id;

    private String name;

    private String phone;

    private String mail;

    public Person(String name) {
        this.name = name;
    }

    public BusinessCard aBusinessCard() {
        return new BusinessCard(name, phone, mail);
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int id() {
        return id;
    }

    private Person() {}
}
