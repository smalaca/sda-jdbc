package com.smalaca.sda.domain;

import java.util.List;

public interface PersonRepository {
    Integer save(Person person);

    void delete(Person person);

    Person find(Integer id);

    List findAll();

    int deleteAll();
}
