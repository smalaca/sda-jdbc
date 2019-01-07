package com.smalaca.sda.repository.mysql;

import com.smalaca.sda.domain.Person;
import com.smalaca.sda.domain.PersonRepository;
import org.hibernate.Session;

import java.util.List;

public class MysqlPersonRepository implements PersonRepository {
    private final Session session;

    public MysqlPersonRepository(Session session) {
        this.session = session;
    }

    @Override
    public Integer save(Person person) {
        session.getTransaction().begin();
        Integer result = (Integer) session.save(person);
        session.getTransaction().commit();
        return result;
    }

    @Override
    public void delete(Person person) {
        session.getTransaction().begin();
        session.delete(person);
        session.getTransaction().commit();
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
        session.getTransaction().begin();
        int removed = session.createQuery("DELETE FROM Person").executeUpdate();
        session.getTransaction().commit();
        return removed;
    }
}
