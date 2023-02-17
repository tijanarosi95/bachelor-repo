package com.ftn.anticancerdrugrecord.service.person;

import com.ftn.anticancerdrugrecord.model.person.Person;
import java.util.List;
import java.util.Optional;

public interface PersonServiceInterface {

    void createPerson(Person person);

    Optional<Person> getPersonById(String id);

    List<Person> getPersonsTreatedByDrug(String drugId);

}
