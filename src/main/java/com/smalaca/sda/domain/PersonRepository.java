package com.smalaca.sda.domain;

public interface PersonRepository {
    Integer save(Person person);

    void delete(Person person);

    Person find(Integer id);
}
