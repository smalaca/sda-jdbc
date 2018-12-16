package com.smalaca.sda.controller;

import com.smalaca.sda.domain.Product;
import com.smalaca.sda.repository.mysql.MySqlRepositoryProduct;
import org.hibernate.Session;

public class ControllerProduct {
    private final Session session;
    private final MySqlRepositoryProduct mySqlRepositoryProduct;

    public ControllerProduct(
            Session session,
            MySqlRepositoryProduct mySqlRepositoryProduct) {
        this.session = session;
        this.mySqlRepositoryProduct = mySqlRepositoryProduct;
    }

    public Integer create(String name, String catalogNumber) {
        Product product = new Product(name, catalogNumber);
        Integer id = null;

        try {
            session.getTransaction().begin();

            id = mySqlRepositoryProduct.save(product);

            session.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
            session.getTransaction().rollback();
        }

        return id;
    }

    public Product find(Integer productId) {
        return mySqlRepositoryProduct.findById(productId);
    }
}
