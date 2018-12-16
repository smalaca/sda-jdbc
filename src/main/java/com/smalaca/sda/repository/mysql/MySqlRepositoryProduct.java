package com.smalaca.sda.repository.mysql;

import com.smalaca.sda.domain.Product;
import org.hibernate.Session;

import java.util.List;

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

    public void update(Product product) {
        session.update(product);
    }

    public void delete(Product product) {
        session.delete(product);
    }

    public List<Product> findAll() {
        return session.createQuery("From Product").list();
    }
}
