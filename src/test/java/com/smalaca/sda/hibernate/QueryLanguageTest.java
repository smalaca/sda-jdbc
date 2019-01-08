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
    private static final String GWEN_STACY = "Gwen Stacy";
    private static final String MARY_JANE_WATSON = "Mary Jane Watson";
    private static final String PETER_PARKER = "Peter Parker";

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
        Person person1 = givenPerson(PETER_PARKER);
        Person person2 = givenPerson(MARY_JANE_WATSON);
        Person person3 = givenPerson(GWEN_STACY);

        List<Person> list = aQuery("FROM Person").list();

        assertThat(list).containsExactlyInAnyOrder(person1, person2, person3);
    }

    @Test
    public void shouldReturnAllPersonsName() {
        givenPersons();

        List<String> list = aQuery("SELECT P.name FROM Person P ").list();

        assertThat(list).containsExactlyInAnyOrder(PETER_PARKER, MARY_JANE_WATSON, GWEN_STACY);
    }

    @Test
    public void shouldReturnPersonByName() {
        givenPersons();

        List<Person> result = aQuery("FROM Person WHERE name = '" + PETER_PARKER + "'").list();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).aBusinessCard().name()).isEqualTo(PETER_PARKER);
    }

    @Test
    public void shouldDeleteAllPersons() {
        givenPersons();

        aSession().getTransaction().begin();
        String hql = "DELETE FROM Person";
        int removed = aQuery(hql).executeUpdate();
        aSession().getTransaction().commit();

        assertThat(removed).isEqualTo(3);
        assertThat(personRepository.findAll()).isEmpty();
    }

    private void givenPersons() {
        givenPerson(PETER_PARKER);
        givenPerson(MARY_JANE_WATSON);
        givenPerson(GWEN_STACY);
    }

    private Query aQuery(String hql) {
        return aSession().createQuery(hql);
    }

    private Person givenPerson(String name) {
        Integer id = personRepository.save(new Person(name));
        return personRepository.find(id);
    }
}
