package com.smalaca.sda.hibernate;

import com.smalaca.sda.domain.Person;
import com.smalaca.sda.domain.PersonRepository;
import com.smalaca.sda.repository.mysql.MysqlPersonRepository;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryLanguageTest extends HibernateTestsSuite {
    private PersonRepository personRepository;

    @Before
    public void initPersonRepository() {
        personRepository = new MysqlPersonRepository(aSession());
    }

    @After
    public void removeAllCreatedPersons() {
        personRepository.deleteAll();
    }

    @Test
    public void shouldReturnEmptyListIfNoPerson() {
        Query query = aQuery("FROM Person");

        assertThat(query.list()).isEmpty();
    }

    @Test
    public void shouldReturnAllPersons() {
        Person person1 = givenPerson("Peter Parker");
        Person person2 = givenPerson("Mary Jane Watson");
        Person person3 = givenPerson("Gwen Stacy");

        List<Person> list = aQuery("FROM Person").list();

        assertThat(list).containsExactlyInAnyOrder(person1, person2, person3);
    }

    @Test
    public void shouldDeleteAllPersons() {
        givenPerson("Peter Parker");
        givenPerson("Mary Jane Watson");
        givenPerson("Gwen Stacy");

        aSession().getTransaction().begin();
        String hql = "DELETE FROM Person";
        int removed = aQuery(hql).executeUpdate();
        aSession().getTransaction().commit();

        assertThat(removed).isEqualTo(3);
        assertThat(personRepository.findAll()).isEmpty();
    }

    private Query aQuery(String hql) {
        return aSession().createQuery(hql);
    }

    private Person givenPerson(String name) {
        Integer id = personRepository.save(new Person(name));
        return personRepository.find(id);
    }
}
