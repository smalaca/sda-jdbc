package com.smalaca.sda;

import com.smalaca.sda.controller.ControllerProduct;
import com.smalaca.sda.controller.ControllerProductFactory;
import com.smalaca.sda.domain.Product;
import com.smalaca.sda.hibernate.HibernateSessionRegistry;
import org.hibernate.Session;

public class ShopApp {
    public static void main( String[] args ) {
        Session session = HibernateSessionRegistry
                .getSessionFactory()
                .openSession();

        ControllerProduct controllerProduct = new ControllerProductFactory().create(session);

        Integer productId = controllerProduct
                .create("playstation 5", "QW3RTY-ABC");

        Product product = controllerProduct.find(productId);
        System.out.println(product);

        controllerProduct.changeDescription(
                productId, "fresh and funky");

        product = controllerProduct.find(productId);
        System.out.println(product);

//        controllerProduct.delete(productId);

        controllerProduct.findAll()
                .forEach(System.out::println);

        session.close();
        HibernateSessionRegistry.shutdown();
    }
}
