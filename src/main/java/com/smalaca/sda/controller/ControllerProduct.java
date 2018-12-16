package com.smalaca.sda.controller;

import com.smalaca.sda.controller.proxy.ProxyId;
import com.smalaca.sda.domain.Product;
import com.smalaca.sda.hibernate.transaction.TransactionalDbOperation;
import com.smalaca.sda.repository.mysql.MySqlRepositoryProduct;

import java.util.List;

public class ControllerProduct {
    private final MySqlRepositoryProduct mySqlRepositoryProduct;
    private final TransactionalDbOperation transactionalDbOperation;

    ControllerProduct(
            MySqlRepositoryProduct mySqlRepositoryProduct,
            TransactionalDbOperation transactionalDbOperation1) {
        this.mySqlRepositoryProduct = mySqlRepositoryProduct;
        this.transactionalDbOperation = transactionalDbOperation1;
    }

    public Integer create(String name, String catalogNumber) {
        Product product = new Product(name, catalogNumber);
        ProxyId productId = new ProxyId();

        transactionalDbOperation.execute(() -> {
            productId.set(mySqlRepositoryProduct.save(product));
        });

        return productId.value();
    }

    public Product find(Integer productId) {
        return mySqlRepositoryProduct.findById(productId);
    }

    public void changeDescription(
            Integer productId, String description) {
        Product product = find(productId);
        product.changeDescription(description);

        transactionalDbOperation.execute(() -> {
            mySqlRepositoryProduct.update(product);
        });
    }

    public void delete(Integer productId) {
        Product product = find(productId);

        transactionalDbOperation.execute(() -> {
            mySqlRepositoryProduct.delete(product);
        });
    }

    public List<Product> findAll() {
        return mySqlRepositoryProduct.findAll();
    }
}
