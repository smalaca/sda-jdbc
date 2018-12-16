package com.smalaca.sda;

import com.smalaca.sda.controller.ControllerProduct;
import com.smalaca.sda.domain.Product;
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

        Integer productId = controllerProduct
                .create("playstation 5", "QW3RTY-ABC");

        Product product = controllerProduct.find(productId);
        System.out.println(product);

        String description = "fresh and funky";
        controllerProduct.changeDescription(
                productId, description);

        product = controllerProduct.find(productId);
        System.out.println(product);

        session.close();
        HibernateSessionRegistry.shutdown();
    }
}
