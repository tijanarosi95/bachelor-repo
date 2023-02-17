package com.ftn.anticancerdrugrecord.controller;

import com.ftn.anticancerdrugrecord.dto.drug.DrugEffectsDTO;
import com.ftn.anticancerdrugrecord.dto.patient.PatientDrugDTO;
import com.ftn.anticancerdrugrecord.model.drug.Drug;
import com.ftn.anticancerdrugrecord.service.drug.DrugServiceInterface;
import java.util.List;
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
@RequestMapping("/drugs")
public class DrugController {

    @Autowired
    private DrugServiceInterface drugServiceInterface;

    @PostMapping
    public void addDrug(@RequestBody Drug drug) {
        drugServiceInterface.createDrug(drug);
    }

    @PostMapping("/person-drug")
    public void addPatientDrug(@RequestBody PatientDrugDTO dto) {
        drugServiceInterface.insertPersonDrug(dto);
    }

    @PostMapping("/effects")
    public void addDrugEffects(@RequestBody DrugEffectsDTO dto) {
        drugServiceInterface.insertDrugEffects(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Drug> loadDrugById(@PathVariable("id") Integer id) {
        final Drug drug = drugServiceInterface.getDrugById(id).orElse(null);
        return new ResponseEntity(drug, HttpStatus.OK);
    }

    @GetMapping("/diseases/{diseaseType}")
    public ResponseEntity<List<Drug>> loadDrugsByDiseaseType(@PathVariable("diseaseType") String diseaseType) {
        final List<Drug> drugs = drugServiceInterface.getDrugsByDiseaseType(diseaseType);
        return new ResponseEntity(drugs, HttpStatus.OK);
    }
}
