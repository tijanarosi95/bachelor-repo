package com.ftn.anticancerdrugrecord.service.disease;

import com.ftn.anticancerdrugrecord.dto.patient.PatientDiseaseDTO;
import com.ftn.anticancerdrugrecord.model.disease.Disease;
import java.util.List;
import java.util.Optional;

public interface DiseaseServiceInterface {

    void createDisease(Disease disease);

    void insertPersonDisease(PatientDiseaseDTO dto);

    Optional<Disease> getDiseaseById(int id);

    Optional<PatientDiseaseDTO> getPatientDiseaseByJmbg(String jmbg);

    List<Disease> getAllDiseases();
}
