package com.ftn.anticancerdrugrecord.service.drug.impl;

import com.ftn.anticancerdrugrecord.dto.drug.DrugEffectsDTO;
import com.ftn.anticancerdrugrecord.dto.patient.PatientDrugDTO;
import com.ftn.anticancerdrugrecord.model.drug.Drug;
import com.ftn.anticancerdrugrecord.service.drug.DrugServiceInterface;
import com.ftn.anticancerdrugrecord.util.InsertUtility;
import com.ftn.anticancerdrugrecord.util.SelectDrugUtility;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrugService implements DrugServiceInterface {

    @Autowired
    private InsertUtility insertUtility;

    @Autowired
    private SelectDrugUtility selectUtility;

    @Override
    public void createDrug(final Drug drug) {
        insertUtility.insertDrug(drug);
    }

    @Override
    public void insertDrugEffects(DrugEffectsDTO drugEffects) {
        insertUtility.insertDrugEffects(drugEffects);
    }

    @Override
    public void insertPersonDrug(PatientDrugDTO dto) {
        insertUtility.insertPersonDrug(dto);
    }

    @Override
    public Optional<Drug> getDrugById(int id) {
        return selectUtility.loadDrugById(id);
    }

    @Override
    public List<Drug> getDrugsByDiseaseType(String type) {
        return selectUtility.loadDrugsByDiseaseType(type);
    }

    @Override
    public List<Drug> getAllDrugs() {
        return selectUtility.loadAllDrugs();
    }
}
