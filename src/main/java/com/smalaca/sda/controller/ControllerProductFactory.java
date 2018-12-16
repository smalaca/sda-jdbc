package com.smalaca.sda.controller;

import com.smalaca.sda.hibernate.transaction.TransactionalDbOperation;
import com.smalaca.sda.repository.mysql.MySqlRepositoryProduct;
import org.hibernate.Session;

public class ControllerProductFactory {
    public ControllerProduct create(Session session) {
        return new ControllerProduct(
                new MySqlRepositoryProduct(session),
                new TransactionalDbOperation(session));
    }
}
