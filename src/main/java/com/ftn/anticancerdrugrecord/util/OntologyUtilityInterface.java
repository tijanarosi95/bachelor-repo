package com.ftn.anticancerdrugrecord.util;

import com.ftn.anticancerdrugrecord.dto.patient.PatientDTO;
import com.ftn.anticancerdrugrecord.model.person.Person;

public interface OntologyUtilityInterface {

    PatientDTO inferPersonFacts(Person person);

}
