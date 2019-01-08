package com.smalaca.sda.hibernate;

import org.hibernate.transform.BasicTransformerAdapter;

class PersonNamesTransformer extends BasicTransformerAdapter {
    @Override
    public PersonNames transformTuple(Object[] tuple, String[] aliases) {
        return new PersonNames((long) tuple[0], (String) tuple[1]);
    }
}
