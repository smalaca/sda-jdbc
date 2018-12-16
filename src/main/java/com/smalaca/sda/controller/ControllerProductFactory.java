package com.smalaca.sda.controller;

import com.smalaca.sda.repository.mysql.MySqlRepositoryProduct;
import org.hibernate.Session;

public class ControllerProductFactory {
    public ControllerProduct create(Session session) {
        MySqlRepositoryProduct mySqlRepositoryProduct =
                new MySqlRepositoryProduct(session);
        return new ControllerProduct(
                session, mySqlRepositoryProduct);
    }
}
