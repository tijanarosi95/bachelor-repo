package com.ftn.anticancerdrugrecord.dto.drug;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DrugDTO {

    private String drugId;

    private String drugName;

    private String activeIngredient;

    private Drug preclinicalTestedDrug;

    private Drug clinicalTestedDrugPhase1;

    private Drug clinicalTestedDrugPhase2;

    private Drug clinicalTestedDrugPhase3;

    private Drug approvedDrug;

    public DrugDTO(String drugId, String name, String activeIngredient) {
        this.drugId = drugId;
        this.drugName = name;
        this.activeIngredient = activeIngredient;
    }
}
