package com.smalaca.sda.hibernate;

import com.smalaca.sda.domain.Person;
import com.smalaca.sda.domain.PersonRepository;
import com.smalaca.sda.repository.mysql.MysqlPersonRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryCriteriaTest extends HibernateTestsSuite {
    private static final String PETER_PARKER = "Peter Parker";
    private static final String MARY_JANE_WATSON = "Mary Jane Watson";
    private static final String GWEN_STACY = "Gwen Stacy";
    private static final String NORMAN_OSBORN = "Norman Osborn";
    private static final String HARRY_OSBORN = "Harry Osborn";
    private static final String BEN_PARKER = "Ben Parker";
    private static final String MAY_PARKER = "May Parker";
    private static final String MILES_MORALES = "Miles Morales";

    private static final String SOME_MAIL = "some.mail@gmail.com";
    private static final String ANOTHER_MAIL = "another.mail@gmail.com";
    private static final String DIFFERENT_MAIL = "different@ma.il";

    private static final String SOME_PHONE = "123 456 789";
    private static final String ANOTHER_PHONE = "111 222 333";
    private static final String DIFFERENT_PHONE = "131 313 131";

    private PersonRepository personRepository;
    private Person peterParker;
    private Person maryJaneWatson;
    private Person harryOsborn;
    private Person berParker;
    private Person mayParker;
    private Person milesMorales;

    @Before
    public void initPersonRepository() {
        personRepository = new MysqlPersonRepository(aSession());
        givenPersons();
    }

    private void givenPersons() {
        peterParker = persistedPerson(PETER_PARKER, SOME_MAIL, SOME_PHONE);
        maryJaneWatson = persistedPerson(MARY_JANE_WATSON, SOME_MAIL, ANOTHER_PHONE);
        persistedPerson(GWEN_STACY, ANOTHER_MAIL, DIFFERENT_PHONE);
        persistedPerson(NORMAN_OSBORN, DIFFERENT_MAIL, SOME_PHONE);
        harryOsborn = persistedPerson(HARRY_OSBORN, SOME_MAIL, SOME_PHONE);
        berParker = persistedPerson(BEN_PARKER, SOME_MAIL, ANOTHER_PHONE);
        mayParker = persistedPerson(new Person(MAY_PARKER));
        milesMorales = persistedPerson(new Person(MILES_MORALES));
    }

    private Person persistedPerson(String name, String mail, String phone) {
        return persistedPerson(aPerson(name, mail, phone));
    }

    private Person persistedPerson(Person person) {
        Integer id = personRepository.save(person);
        return personRepository.find(id);
    }

    private Person aPerson(String name, String mail, String phone) {
        Person person = new Person(name);
        person.setMail(mail);
        person.setPhone(phone);
        return person;
    }

    @After
    public void removeAllCreatedPersons() {
        personRepository.deleteAll();
    }

    @Test
    public void shouldListAllPersons() {
        CriteriaQuery<Person> query = aSession().getCriteriaBuilder().createQuery(Person.class);
        query.from(Person.class);
        List<Person> list = aSession().createQuery(query).list();

        assertThat(list).containsExactlyInAnyOrderElementsOf(personRepository.findAll());
    }

    @Test
    public void shouldListAllParkers() {
        CriteriaBuilder builder = aSession().getCriteriaBuilder();
        CriteriaQuery<Person> query = builder.createQuery(Person.class);
        Root<Person> root = query.from(Person.class);
        query.where(builder.like(root.get("name"), "% Parker"));
        List<Person> list = aSession().createQuery(query).list();

        assertThat(list).containsExactlyInAnyOrder(peterParker, berParker, mayParker);
    }

    @Test
    public void shouldListAllWithSomeMail() {
        CriteriaBuilder builder = aSession().getCriteriaBuilder();
        CriteriaQuery<Person> query = builder.createQuery(Person.class);
        Root<Person> root = query.from(Person.class);
        query.where(builder.like(root.get("mail"), SOME_MAIL));
        List<Person> list = aSession().createQuery(query).list();

        assertThat(list).containsExactlyInAnyOrder(peterParker, berParker, maryJaneWatson, harryOsborn);
    }

    @Test
    public void shouldListAllParkersWithSpecificMail() {
        CriteriaBuilder builder = aSession().getCriteriaBuilder();
        CriteriaQuery<Person> query = builder.createQuery(Person.class);
        Root<Person> root = query.from(Person.class);
        Expression<Boolean> parkers = builder.like(root.get("name"), "% Parker");
        Expression<Boolean> someMail = builder.like(root.get("mail"), SOME_MAIL);
        query.where(builder.and(parkers, someMail));
        List<Person> list = aSession().createQuery(query).list();

        assertThat(list).containsExactlyInAnyOrder(peterParker, berParker);
    }

    @Test
    public void shouldListAllParkersAndPeopleWithSpecificMail() {
        CriteriaBuilder builder = aSession().getCriteriaBuilder();
        CriteriaQuery<Person> query = builder.createQuery(Person.class);
        Root<Person> root = query.from(Person.class);
        Expression<Boolean> parkers = builder.like(root.get("name"), "% Parker");
        Expression<Boolean> someMail = builder.like(root.get("mail"), SOME_MAIL);
        query.where(builder.or(parkers, someMail));
        List<Person> list = aSession().createQuery(query).list();

        assertThat(list).containsExactlyInAnyOrder(peterParker, berParker, maryJaneWatson, harryOsborn, mayParker);
    }

    @Test
    public void shouldFindAllPeopleWithoutMail() {
        CriteriaBuilder builder = aSession().getCriteriaBuilder();
        CriteriaQuery<Person> query = builder.createQuery(Person.class);
        Root<Person> root = query.from(Person.class);
        query.where(builder.isNull(root.get("mail")));
        List<Person> list = aSession().createQuery(query).list();

        assertThat(list).containsExactlyInAnyOrder(mayParker, milesMorales);
    }

}
