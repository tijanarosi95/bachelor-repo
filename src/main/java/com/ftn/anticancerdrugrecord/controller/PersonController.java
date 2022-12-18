package com.ftn.anticancerdrugrecord.controller;

import com.ftn.anticancerdrugrecord.model.person.Person;
import com.ftn.anticancerdrugrecord.service.person.PersonServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonServiceInterface personServiceInterface;

    @PostMapping
    public void addPerson(@RequestBody Person person) {
        personServiceInterface.createPerson(person);
    }
}
