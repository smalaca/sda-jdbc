package com.smalaca.sda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private int id;

    private String name;

    @Column(name = "catalog_number")
    private String catalogNumber;

    private String description;

    public Product(String name, String catalogNumber) {
        this.name = name;
        this.catalogNumber = catalogNumber;
    }

    private Product() {}
}
