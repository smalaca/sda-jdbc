package com.smalaca.sda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Price {
    @Id
    @GeneratedValue
    @Column(name = "price_id")
    private int id;
    private float value;
    private String currency;

    public Price(float value, String currency) {
        this.value = value;
        this.currency = currency;
    }

    private Price() {}

    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", value=" + value +
                ", currency='" + currency + '\'' +
                '}';
    }
}
