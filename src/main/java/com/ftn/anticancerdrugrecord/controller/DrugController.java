package com.ftn.anticancerdrugrecord.controller;

import com.ftn.anticancerdrugrecord.dto.drug.DrugDTO;
import com.ftn.anticancerdrugrecord.dto.drug.DrugEffectsDTO;
import com.ftn.anticancerdrugrecord.dto.drug.DrugUpdateDTO;
import com.ftn.anticancerdrugrecord.dto.patient.PatientDrugDTO;
import com.ftn.anticancerdrugrecord.model.drug.Drug;
import com.ftn.anticancerdrugrecord.service.drug.DrugServiceInterface;
import com.ftn.anticancerdrugrecord.util.OntologyUtilityInterface;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/drugs")
public class DrugController {

    @Autowired
    private DrugServiceInterface drugServiceInterface;

    @Autowired
    private OntologyUtilityInterface ontologyUtilityInterface;

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

    @PutMapping
    public ResponseEntity<Boolean> updateDrugEffects(@RequestBody DrugUpdateDTO drug) {
        var updated = drugServiceInterface.updateDrugEffects(drug);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Drug> loadDrugById(@PathVariable("id") Integer id) {
        final Drug drug = drugServiceInterface.getDrugById(id).orElse(null);
        return new ResponseEntity(drug, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Drug>> loadAllDrugs() {
        final List<Drug> drugs = drugServiceInterface.getAllDrugs();
        return new ResponseEntity(drugs, HttpStatus.OK);
    }

    @GetMapping("/diseases/{diseaseType}")
    public ResponseEntity<List<Drug>> loadDrugsByDiseaseType(@PathVariable("diseaseType") String diseaseType) {
        final List<Drug> drugs = drugServiceInterface.getDrugsByDiseaseType(diseaseType);
        return new ResponseEntity(drugs, HttpStatus.OK);
    }

    @PostMapping("/infer-facts")
    public ResponseEntity<DrugDTO> inferDrugFacts(@RequestBody DrugEffectsDTO drugEffectsDTO) {
        final DrugDTO drugDTO = ontologyUtilityInterface.inferDrugFacts(drugEffectsDTO);
        return new ResponseEntity<>(drugDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteDrug(@PathVariable("id") String id) {
        var deleted = drugServiceInterface.deleteDrug(id);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }
}
