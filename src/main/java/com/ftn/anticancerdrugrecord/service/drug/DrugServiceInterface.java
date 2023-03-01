package com.ftn.anticancerdrugrecord.service.drug;

import com.ftn.anticancerdrugrecord.dto.drug.DrugEffectsDTO;
import com.ftn.anticancerdrugrecord.dto.patient.PatientDrugDTO;
import com.ftn.anticancerdrugrecord.model.drug.Drug;
import java.util.List;
import java.util.Optional;

public interface DrugServiceInterface {

    void createDrug(Drug drug);

    void insertDrugEffects(DrugEffectsDTO drugEffects);

    void insertPersonDrug(PatientDrugDTO dto);

    Optional<Drug> getDrugById(int id);

    List<Drug> getDrugsByDiseaseType(String type);

    List<Drug> getAllDrugs();
}
