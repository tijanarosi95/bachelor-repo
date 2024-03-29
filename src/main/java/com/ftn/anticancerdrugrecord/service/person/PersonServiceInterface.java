package com.ftn.anticancerdrugrecord.service.person;

import com.ftn.anticancerdrugrecord.dto.patient.PatientUpdateDTO;
import com.ftn.anticancerdrugrecord.model.person.Person;
import java.util.List;
import java.util.Optional;

public interface PersonServiceInterface {

    List<Person> getAll();

    void createPerson(Person person);

    Optional<Person> getPersonById(String id);

    List<Person> getPersonsTreatedByDrug(String drugName);

    List<Person> getPersonsTreatedByDrug();

    boolean updatePerson(PatientUpdateDTO patient);

    boolean deletePerson(String jmbg);
}
