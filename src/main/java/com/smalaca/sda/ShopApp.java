package com.smalaca.sda;

import com.smalaca.sda.controller.ControllerProduct;
import com.smalaca.sda.hibernate.HibernateSessionRegistry;
import com.smalaca.sda.repository.mysql.MySqlRepositoryProduct;
import org.hibernate.Session;

public class ShopApp {
    public static void main( String[] args ) {
        Session session = HibernateSessionRegistry
                .getSessionFactory()
                .openSession();

        MySqlRepositoryProduct mySqlRepositoryProduct =
                new MySqlRepositoryProduct(session);
        ControllerProduct controllerProduct =
                new ControllerProduct(session, mySqlRepositoryProduct);

        // save product -- start
        String name = "laptop";
        String catalogNumber = "QW3RTY";
        Integer productId = controllerProduct
                .create(name, catalogNumber);

        System.out.println(productId);

        session.close();
        HibernateSessionRegistry.shutdown();
    }
}
