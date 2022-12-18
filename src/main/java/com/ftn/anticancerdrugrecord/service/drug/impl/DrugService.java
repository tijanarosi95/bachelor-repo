package com.ftn.anticancerdrugrecord.service.drug.impl;

import com.ftn.anticancerdrugrecord.model.drug.Drug;
import com.ftn.anticancerdrugrecord.service.drug.DrugServiceInterface;
import com.ftn.anticancerdrugrecord.util.InsertUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrugService implements DrugServiceInterface {

    @Autowired
    private InsertUtility insertUtility;

    @Override
    public void createDrug(final Drug drug) {
        insertUtility.insertDrug(drug);
    }
}
