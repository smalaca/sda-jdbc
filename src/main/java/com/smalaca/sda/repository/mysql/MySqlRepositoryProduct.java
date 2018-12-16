package com.smalaca.sda.repository.mysql;

import com.smalaca.sda.domain.Product;
import org.hibernate.Session;

public class MySqlRepositoryProduct {
    private final Session session;

    public MySqlRepositoryProduct(Session session) {
        this.session = session;
    }

    public Integer save(Product product) {
        return (Integer) session.save(product);
    }

    public Product findById(Integer productId) {
        return session.get(Product.class, productId);
    }
}
