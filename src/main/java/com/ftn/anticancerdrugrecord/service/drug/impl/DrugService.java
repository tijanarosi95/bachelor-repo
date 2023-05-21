package com.ftn.anticancerdrugrecord.service.drug.impl;

import com.ftn.anticancerdrugrecord.dto.drug.DrugEffectsDTO;
import com.ftn.anticancerdrugrecord.dto.drug.DrugUpdateDTO;
import com.ftn.anticancerdrugrecord.dto.patient.PatientDrugDTO;
import com.ftn.anticancerdrugrecord.model.drug.Drug;
import com.ftn.anticancerdrugrecord.service.drug.DrugServiceInterface;
import com.ftn.anticancerdrugrecord.util.InsertUtility;
import com.ftn.anticancerdrugrecord.util.SelectDrugStatistics;
import com.ftn.anticancerdrugrecord.util.SelectDrugUtility;
import com.ftn.anticancerdrugrecord.util.UpdateDrugUtility;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.jena.base.Sys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrugService implements DrugServiceInterface {

    @Autowired
    private InsertUtility insertUtility;

    @Autowired
    private SelectDrugUtility selectUtility;

    @Autowired
    private SelectDrugStatistics drugStatistics;

    @Autowired
    private UpdateDrugUtility updateUtility;

    @Override
    public void createDrug(final Drug drug) {
        insertUtility.insertDrug(drug);
    }

    @Override
    public void insertDrugEffects(final DrugEffectsDTO drugEffects) {
        insertUtility.insertDrugEffects(drugEffects);
    }

    @Override
    public boolean updateDrugEffects(final DrugUpdateDTO drug) {
        return updateUtility.updateDrug(drug);
    }

    @Override
    public void insertPersonDrug(PatientDrugDTO dto) {
        insertUtility.insertPersonDrug(dto);
    }

    @Override
    public boolean deleteDrug(String drugId) {
        return updateUtility.removeDrug(drugId);
    }

    @Override
    public Optional<Drug> getDrugById(int id) {
        return selectUtility.loadDrugById(id);
    }

    @Override
    public Optional<PatientDrugDTO> getPatientTreatedDrug(String jmbg) {
        var patientDrugData = selectUtility.loadPatientTreatedDrug(jmbg);
        if (patientDrugData.isPresent()) {
            var drug = selectUtility.loadDrugByName(patientDrugData.get().getDrugName());
            var drugId = drug.isPresent() ? drug.get().getDrugId() : 0;
            patientDrugData.get().setDrugId(String.valueOf(drugId));
        }
        return patientDrugData;
    }

    @Override
    public List<Drug> getDrugsByDiseaseType(String type) {
        return selectUtility.loadDrugsByDiseaseType(type);
    }

    @Override
    public List<Drug> getAllDrugs() {
        return selectUtility.loadAllDrugs();
    }

    @Override
    public List<Double> getDrugStatistics(int id) {
        final List<Double> statistics = new ArrayList<>();
        var countHasEfficacy = drugStatistics.getHasEfficacyCount(id);
        var countSideEffects = drugStatistics.getHasSideEffectsCount(id);
        var countTherapeuticEffect = drugStatistics.getHasTherapeuticEffectCount(id);
        var countIsDoseRanged = drugStatistics.getIsDoseRangedCount(id);
        var countHasToxicity = drugStatistics.getHasToxicityCount(id);

        var total = countHasEfficacy + countSideEffects + countTherapeuticEffect + countIsDoseRanged + countHasToxicity;
        statistics.add(0, getPercentage(countHasEfficacy, total));
        statistics.add(1, getPercentage(countSideEffects, total));
        statistics.add(2, getPercentage(countTherapeuticEffect, total));
        statistics.add(3, getPercentage(countIsDoseRanged, total));
        statistics.add(4, getPercentage(countHasToxicity, total));
        return statistics;
    }

    private double getPercentage(int value, int total) {
        System.out.println("In get perctange funstion: " + value + " " + total);
        var result = (value * 100.0f) / total;
        System.out.println("Result " + result);

        return result;
    }
}
