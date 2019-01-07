package com.smalaca.sda.hibernate;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;

class HibernateTestsSuite {
    private Session session;

    @Before
    public void initHibernateSession() {
        session = HibernateSessionRegistry
                .getSessionFactory()
                .openSession();
    }

    @After
    public void closeSession() {
        session.close();
        HibernateSessionRegistry.shutdown();
    }

    protected Session aSession() {
        return session;
    }
}
