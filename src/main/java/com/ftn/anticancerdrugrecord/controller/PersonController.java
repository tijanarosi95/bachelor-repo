package com.ftn.anticancerdrugrecord.controller;

import com.ftn.anticancerdrugrecord.dto.patient.PatientDTO;
import com.ftn.anticancerdrugrecord.model.person.Person;
import com.ftn.anticancerdrugrecord.service.person.PersonServiceInterface;
import com.ftn.anticancerdrugrecord.util.OntologyUtilityInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonServiceInterface personServiceInterface;

    @Autowired
    private OntologyUtilityInterface ontologyUtilityInterface;

    @PostMapping
    public void addPerson(@RequestBody Person person) {
        personServiceInterface.createPerson(person);
    }

    @GetMapping("/{id}")
    public ResponseEntity loadPersonById(@PathVariable("id") String id) {
        final Person person = personServiceInterface.getPersonById(id).orElse(null);
        return new ResponseEntity(person, HttpStatus.OK);
    }

    @PostMapping("/infer-facts")
    public ResponseEntity<PatientDTO> testForPersonInferredFacts(@RequestBody Person p) {
        final PatientDTO person = ontologyUtilityInterface.inferPersonFacts(p);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

}
