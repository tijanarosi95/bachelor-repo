package com.ftn.anticancerdrugrecord.controller;

import com.ftn.anticancerdrugrecord.model.disease.Disease;
import com.ftn.anticancerdrugrecord.service.disease.DiseaseServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/diseases")
public class DiseaseController {

    @Autowired
    private DiseaseServiceInterface diseaseServiceInterface;

    @PostMapping
    public void addDisease(@RequestBody Disease disease) {
        diseaseServiceInterface.createDisease(disease);
    }
}
