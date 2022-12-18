package com.ftn.anticancerdrugrecord.service.disease.impl;

import com.ftn.anticancerdrugrecord.model.disease.Disease;
import com.ftn.anticancerdrugrecord.service.disease.DiseaseServiceInterface;
import com.ftn.anticancerdrugrecord.util.InsertUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiseaseService implements DiseaseServiceInterface {

    @Autowired
    private InsertUtility insertUtility;

    @Override
    public void createDisease(final Disease disease) {
        insertUtility.insertDisease(disease);
    }
}
