package com.ftn.anticancerdrugrecord.service.drug.impl;

import com.ftn.anticancerdrugrecord.model.drug.Drug;
import com.ftn.anticancerdrugrecord.service.drug.DrugServiceInterface;
import com.ftn.anticancerdrugrecord.util.InsertUtility;
import com.ftn.anticancerdrugrecord.util.SelectUtility;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrugService implements DrugServiceInterface {

    @Autowired
    private InsertUtility insertUtility;

    @Autowired
    private SelectUtility selectUtility;

    @Override
    public void createDrug(final Drug drug) {
        insertUtility.insertDrug(drug);
    }

    @Override
    public Optional<Drug> getDrugById(int id) {
        return selectUtility.loadDrugById(id);
    }

    @Override
    public List<Drug> getDrugsByDiseaseType(String type) {
        return selectUtility.loadDrugsByDiseaseType(type);
    }
}
