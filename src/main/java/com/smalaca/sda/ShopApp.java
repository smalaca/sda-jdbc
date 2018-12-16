package com.smalaca.sda;

import com.smalaca.sda.domain.Product;
import com.smalaca.sda.hibernate.HibernateSessionRegistry;
import org.hibernate.Session;

public class ShopApp {
    public static void main( String[] args ) {
        Session session = HibernateSessionRegistry
                .getSessionFactory()
                .openSession();

        try {
            session.getTransaction().begin();

            String name = "laptop";
            String catalogNumber = "QW3RTY";
            Product product = new Product(name, catalogNumber);

            Integer productId = (Integer) session.save(product);

            System.out.println(productId);

            session.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
            session.getTransaction().rollback();
        }

        session.close();

        HibernateSessionRegistry.shutdown();
    }
}
