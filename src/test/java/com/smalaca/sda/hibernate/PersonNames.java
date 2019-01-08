package com.smalaca.sda.hibernate;

import java.util.Objects;

class PersonNames {
    private final long amount;
    private final String name;

    PersonNames(long amount, String name) {
        this.amount = amount;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonNames that = (PersonNames) o;
        return amount == that.amount &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, name);
    }
}
