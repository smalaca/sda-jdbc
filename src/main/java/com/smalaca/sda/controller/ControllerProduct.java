package com.smalaca.sda.controller;

import com.smalaca.sda.controller.proxy.ProxyId;
import com.smalaca.sda.domain.Product;
import com.smalaca.sda.hibernate.transaction.SafeDbOperation;
import com.smalaca.sda.hibernate.transaction.TransactionalDbOperation;
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
        ProxyId productId = new ProxyId();

        safeOperation(() -> {
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

        safeOperation(() -> mySqlRepositoryProduct.update(product));
    }

    public void delete(Integer productId) {
        Product product = find(productId);

        safeOperation(() -> mySqlRepositoryProduct.delete(product));
    }

    private void safeOperation(SafeDbOperation callback) {
        new TransactionalDbOperation(session).execute(callback);
    }
}
