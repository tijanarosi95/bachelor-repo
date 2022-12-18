package com.ftn.anticancerdrugrecord.controller;

import com.ftn.anticancerdrugrecord.model.drug.Drug;
import com.ftn.anticancerdrugrecord.service.drug.DrugServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/drugs")
public class DrugController {

    @Autowired
    private DrugServiceInterface drugServiceInterface;

    @PostMapping
    public void addDrug(@RequestBody Drug drug) {
        drugServiceInterface.createDrug(drug);
    }
}
