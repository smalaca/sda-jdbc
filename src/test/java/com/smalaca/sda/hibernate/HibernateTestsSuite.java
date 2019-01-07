package com.smalaca.sda.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
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

    protected Session aSession() {
        return session;
    }

    @AfterClass
    public static void closeSessionRegistry() {
        HibernateSessionRegistry.shutdown();
    }
}
