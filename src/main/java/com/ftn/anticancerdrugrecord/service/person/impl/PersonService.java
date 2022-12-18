package com.ftn.anticancerdrugrecord.service.person.impl;

import com.ftn.anticancerdrugrecord.model.person.Person;
import com.ftn.anticancerdrugrecord.service.person.PersonServiceInterface;
import com.ftn.anticancerdrugrecord.util.InsertUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService implements PersonServiceInterface {

    @Autowired
    private InsertUtility insertUtility;

    @Override
    public void createPerson(final Person person) {
        insertUtility.insertPerson(person);
    }
}
