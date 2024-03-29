package com.ftn.anticancerdrugrecord.controller;

import com.ftn.anticancerdrugrecord.dto.disease.DiseaseUpdateDTO;
import com.ftn.anticancerdrugrecord.dto.drug.DrugUpdateDTO;
import com.ftn.anticancerdrugrecord.dto.patient.PatientDiseaseDTO;
import com.ftn.anticancerdrugrecord.model.disease.Disease;
import com.ftn.anticancerdrugrecord.service.disease.DiseaseServiceInterface;
import java.util.List;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/diseases")
public class DiseaseController {

    @Autowired
    private DiseaseServiceInterface diseaseServiceInterface;

    @PostMapping
    public void addDisease(@RequestBody Disease disease) {
        diseaseServiceInterface.createDisease(disease);
    }

    @PostMapping("/person-disease")
    public void addPatientDisease(@RequestBody PatientDiseaseDTO dto) throws OWLOntologyCreationException {
        diseaseServiceInterface.insertPersonDisease(dto);
    }

    @PutMapping
    public ResponseEntity<Boolean> updateDisease(@RequestBody DiseaseUpdateDTO disease) {
        var updated = diseaseServiceInterface.updateDisease(disease);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Disease> getDiseaseById(@PathVariable ("id") Integer id) {
        var disease = diseaseServiceInterface.getDiseaseById(id).orElse(null);
        return new ResponseEntity(disease, HttpStatus.OK);
    }

    @GetMapping("/person-disease/{jmbg}")
    public ResponseEntity<PatientDiseaseDTO> getPatientDiseaseById(@PathVariable ("jmbg") String jmbg) {
        var patientDisease = diseaseServiceInterface.getPatientDiseaseByJmbg(jmbg).orElse(null);
        return new ResponseEntity(patientDisease, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Disease>> getAllDiseases() {
        var diseases = diseaseServiceInterface.getAllDiseases();
        return new ResponseEntity<>(diseases, HttpStatus.OK);
    }

}
