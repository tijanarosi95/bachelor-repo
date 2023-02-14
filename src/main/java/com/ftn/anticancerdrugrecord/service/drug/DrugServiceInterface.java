package com.ftn.anticancerdrugrecord.service.drug;

import com.ftn.anticancerdrugrecord.model.drug.Drug;
import java.util.List;
import java.util.Optional;

public interface DrugServiceInterface {

    void createDrug(Drug drug);

    Optional<Drug> getDrugById(int id);

    List<Drug> getDrugsByDiseaseType(String type);
}
