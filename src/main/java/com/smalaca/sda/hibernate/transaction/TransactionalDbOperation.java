package com.smalaca.sda.hibernate.transaction;

import org.hibernate.Session;

public class TransactionalDbOperation {
    private final Session session;

    public TransactionalDbOperation(Session session) {
        this.session = session;
    }

    public void execute(SafeDbOperation dbOperation) {
        try {
            session.getTransaction().begin();

            dbOperation.execute();

            session.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
            session.getTransaction().rollback();
        }
    }
}
