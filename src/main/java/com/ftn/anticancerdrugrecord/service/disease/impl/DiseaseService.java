package com.ftn.anticancerdrugrecord.service.disease.impl;

import com.ftn.anticancerdrugrecord.dto.disease.DiseaseDTO;
import com.ftn.anticancerdrugrecord.dto.disease.DiseaseUpdateDTO;
import com.ftn.anticancerdrugrecord.dto.patient.PatientDiseaseDTO;
import com.ftn.anticancerdrugrecord.model.disease.Disease;
import com.ftn.anticancerdrugrecord.service.disease.DiseaseServiceInterface;
import com.ftn.anticancerdrugrecord.util.InsertUtility;
import com.ftn.anticancerdrugrecord.util.SelectDiseaseUtility;
import com.ftn.anticancerdrugrecord.util.UpdateDiseaseUtility;
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

    private UpdateDiseaseUtility updateUtility;

    @Override
    public void createDisease(final Disease disease) {
        insertUtility.insertDisease(disease);
    }

    @Override
    public void insertPersonDisease(PatientDiseaseDTO dto) {
        insertUtility.insertPersonDisease(dto);
    }

    @Override
    public boolean updateDisease(DiseaseUpdateDTO dto) {
        return updateUtility.updateDisease(dto);
    }

    @Override
    public Optional<Disease> getDiseaseById(int id) {
        return selectUtility.loadDiseaseById(id);
    }

    @Override
    public Optional<PatientDiseaseDTO> getPatientDiseaseByJmbg(String jmbg) {
        var patientDiseaseData = selectUtility.loadPatientDisease(jmbg);
        if (patientDiseaseData.isPresent()) {
            var diseaseName = patientDiseaseData.get().getDisease().getName();
            var disease = selectUtility.loadDiseaseByName(diseaseName);
            patientDiseaseData.get().setDisease(new DiseaseDTO(disease.orElseThrow()));
        }
        return patientDiseaseData;
    }

    @Override
    public List<Disease> getAllDiseases() {
        return selectUtility.loadAllDiseases();
    }
}
