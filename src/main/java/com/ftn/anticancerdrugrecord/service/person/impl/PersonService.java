package com.ftn.anticancerdrugrecord.service.person.impl;

import com.ftn.anticancerdrugrecord.model.person.Person;
import com.ftn.anticancerdrugrecord.service.person.PersonServiceInterface;
import com.ftn.anticancerdrugrecord.util.InsertUtility;
import org.apache.jena.shared.JenaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService implements PersonServiceInterface {

    @Autowired
    private InsertUtility insertUtility;

    @Override
    public void createPerson(final Person person) {
        try {
            if (person != null) {
                insertUtility.insertPerson(person);

                if (person.getHasDisease() != null) {
                    insertUtility.insertDisease(person.getHasDisease());
                }
                if (person.getIsTreatedWith() != null) {
                    insertUtility.insertDrug(person.getIsTreatedWith());
                }
                if (person.getLifeQuality() != null) {
                    insertUtility.insertLifeQuality(person.getLifeQuality());
                }
            }
        } catch (JenaException ex) {
            ex.printStackTrace();
        }
    }
}
