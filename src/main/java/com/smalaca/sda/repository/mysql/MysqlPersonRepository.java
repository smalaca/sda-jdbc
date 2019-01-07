package com.smalaca.sda.repository.mysql;

import com.smalaca.sda.domain.Person;
import com.smalaca.sda.domain.PersonRepository;
import org.hibernate.Session;

import java.util.List;
import java.util.function.Supplier;

public class MysqlPersonRepository implements PersonRepository {
    private final Session session;

    public MysqlPersonRepository(Session session) {
        this.session = session;
    }

    @Override
    public Integer save(Person person) {
        return transactional(() -> (Integer) session.save(person));
    }

    @Override
    public void delete(Person person) {
        transactional(() -> session.delete(person));
    }

    private void transactional(Transactional transactional) {
        session.getTransaction().begin();
        transactional.invoke();
        session.getTransaction().commit();
    }

    private interface Transactional {
        void invoke();
    }

    @Override
    public Person find(Integer id) {
        return session.get(Person.class, id);
    }

    @Override
    public List<Person> findAll() {
        return session.createQuery("FROM Person").list();
    }

    @Override
    public int deleteAll() {
        return transactional(() -> session.createQuery("DELETE FROM Person").executeUpdate());
    }

    private <T> T transactional(Supplier<T> supplier) {
        session.getTransaction().begin();
        T result = supplier.get();
        session.getTransaction().commit();

        return result;
    }
}
