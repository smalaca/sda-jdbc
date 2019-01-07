package com.smalaca.sda.domain;

public class BusinessCard {
    private final String name;
    private final String phone;
    private final String mail;

    BusinessCard(String name, String phone, String mail) {
        this.name = name;
        this.phone = phone;
        this.mail = mail;
    }

    public String name() {
        return name;
    }

    public String phone() {
        return phone;
    }

    public String mail() {
        return mail;
    }
}
