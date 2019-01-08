package com.smalaca.sda.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class HibernateTestsSuite {
    private static SessionFactory sessionFactory;
    private Session session;

    @BeforeClass
    public static void initSessionFactory() {
        sessionFactory = HibernateSessionRegistry.getSessionFactory();
    }

    @Before
    public void initSession() {
        session = sessionFactory.openSession();
    }

    @After
    public void closeSession() {
        session.close();
    }


    Query aQuery(String hql) {
        return aSession().createQuery(hql);
    }

    Session aSession() {
        return session;
    }

    @AfterClass
    public static void closeSessionRegistry() {
        HibernateSessionRegistry.shutdown();
    }
}
