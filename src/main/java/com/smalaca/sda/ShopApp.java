package com.smalaca.sda;

import com.smalaca.sda.domain.Product;
import com.smalaca.sda.hibernate.HibernateSessionRegistry;
import com.smalaca.sda.repository.mysql.MySqlRepositoryProduct;
import org.hibernate.Session;

public class ShopApp {
    public static void main( String[] args ) {
        Session session = HibernateSessionRegistry
                .getSessionFactory()
                .openSession();


        // save product -- start
        String name = "laptop";
        String catalogNumber = "QW3RTY";
        Product product = new Product(name, catalogNumber);

        try {
            session.getTransaction().begin();

            Integer productId =
                    new MySqlRepositoryProduct(session)
                        .save(product);

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
