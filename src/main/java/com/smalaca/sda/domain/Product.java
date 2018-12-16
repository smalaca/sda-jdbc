package com.smalaca.sda.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(
                name = "longDescription",
                column = @Column(name = "long_description")
        )
    })
    private Description description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "price_id")
    private Price price;

    public Product(String name, String catalogNumber, Price price) {
        this.name = name;
        this.catalogNumber = catalogNumber;
        this.price = price;
    }

    private Product() {}

    public void changeDescription(String description) {
        this.description = new Description(
                description, description);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", catalogNumber='" + catalogNumber + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
