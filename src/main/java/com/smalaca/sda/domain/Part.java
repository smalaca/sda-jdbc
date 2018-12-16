package com.smalaca.sda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Part {
    @Id
    @GeneratedValue
    @Column(name = "part_id")
    private int id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Part(String name) {
        this.name = name;
    }

    private Part() {}

    @Override
    public String toString() {
        return "Part{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    void set(Product product) {
        this.product = product;
    }
}
