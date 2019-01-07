package com.smalaca.sda.hibernate;

import org.hibernate.query.Query;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryLanguageTest extends HibernateTestsSuite {
    @Test
    public void shouldReturnEmptyListIfNoPerson() {
        String hql = "FROM Person";
        Query query = aSession().createQuery(hql);

        assertThat(query.list()).isEmpty();
    }
}
