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
    private static final String GWEN_STACY_MAIL = "gwen.stacy@bugle.com";

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
        Person peterParker = givenPerson(PETER_PARKER);
        Person maryJaneWatson = givenPerson(MARY_JANE_WATSON);
        Person gwenStacy = givenPerson(GWEN_STACY);

        List<Person> list = aQuery("FROM Person").list();

        assertThat(list).containsExactlyInAnyOrder(peterParker, maryJaneWatson, gwenStacy);
    }

    @Test
    public void shouldReturnAllPersonsOrderedByName() {
        Person peterParker = givenPerson(PETER_PARKER);
        Person maryJaneWatson = givenPerson(MARY_JANE_WATSON);
        Person gwenStacy = givenPerson(GWEN_STACY);

        List<Person> list = aQuery("FROM Person ORDER BY name").list();

        assertThat(list).containsExactly(gwenStacy, maryJaneWatson, peterParker);
    }

    @Test
    public void shouldReturnListOfAmountOfPersonsWithTheSameName() {
        givenPersons();
        givenPerson(PETER_PARKER);
        givenPerson(PETER_PARKER);
        givenPerson(MARY_JANE_WATSON);
        givenPerson(GWEN_STACY);

        List<PersonNames> list = aQuery("SELECT count(*) AS amount, P.name FROM Person P GROUP BY P.name ORDER BY P.name")
                .setResultTransformer(new PersonNamesTransformer())
                .list();

        assertThat(list).hasSize(3);
        assertThat(list).containsExactly(new PersonNames(2, GWEN_STACY), new PersonNames(2, MARY_JANE_WATSON), new PersonNames(3, PETER_PARKER));
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
    public void shouldReturnPersonByNameWithNamedParameter() {
        givenPersons();

        Query query = aQuery("FROM Person WHERE name = :name");
        query.setParameter("name", PETER_PARKER);
        List<Person> result = query.list();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).aBusinessCard().name()).isEqualTo(PETER_PARKER);
    }

    @Test
    public void shouldDeleteAllPersons() {
        givenPersons();

        beginTransaction();
        int removed = aQuery("DELETE FROM Person").executeUpdate();
        commitTransaction();

        assertThat(removed).isEqualTo(3);
        assertThat(personRepository.findAll()).isEmpty();
    }

    @Test
    public void shouldUpdatePersonEmail() {
        givenPerson(PETER_PARKER);
        givenPerson(MARY_JANE_WATSON);
        Person gwenStacy = givenPerson(GWEN_STACY);

        beginTransaction();
        Query query = aQuery("UPDATE Person SET mail = :mail WHERE id = :id");
        query.setParameter("id", gwenStacy.id());
        query.setParameter("mail", GWEN_STACY_MAIL);
        int result = query.executeUpdate();
        commitTransaction();

        aSession().refresh(gwenStacy);

        assertThat(result).isEqualTo(1);
        assertThat(gwenStacy.aBusinessCard().mail()).isEqualTo(GWEN_STACY_MAIL);
    }

    private void commitTransaction() {
        aSession().getTransaction().commit();
    }

    private void beginTransaction() {
        aSession().getTransaction().begin();
    }

    private void givenPersons() {
        givenPerson(PETER_PARKER);
        givenPerson(MARY_JANE_WATSON);
        givenPerson(GWEN_STACY);
    }

    private Person givenPerson(String name) {
        Integer id = personRepository.save(new Person(name));
        return personRepository.find(id);
    }
}
