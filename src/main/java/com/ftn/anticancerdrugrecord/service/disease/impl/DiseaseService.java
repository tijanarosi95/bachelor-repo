package com.ftn.anticancerdrugrecord.service.disease.impl;

import com.ftn.anticancerdrugrecord.dto.patient.PatientDiseaseDTO;
import com.ftn.anticancerdrugrecord.model.disease.Disease;
import com.ftn.anticancerdrugrecord.service.disease.DiseaseServiceInterface;
import com.ftn.anticancerdrugrecord.util.InsertUtility;
import com.ftn.anticancerdrugrecord.util.SelectDiseaseUtility;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiseaseService implements DiseaseServiceInterface {

    @Autowired
    private InsertUtility insertUtility;

    @Autowired
    private SelectDiseaseUtility selectUtility;

    @Override
    public void createDisease(final Disease disease) {
        insertUtility.insertDisease(disease);
    }

    @Override
    public void insertPersonDisease(PatientDiseaseDTO dto) {
        insertUtility.insertPersonDisease(dto);
    }

    @Override
    public Optional<Disease> getDiseaseById(int id) {
        return selectUtility.loadDiseaseById(id);
    }

    @Override
    public Optional<PatientDiseaseDTO> getPatientDiseaseByJmbg(String jmbg) {
        return selectUtility.loadPatientDisease(jmbg);
    }

    @Override
    public List<Disease> getAllDiseases() {
        return selectUtility.loadAllDiseases();
    }
}
