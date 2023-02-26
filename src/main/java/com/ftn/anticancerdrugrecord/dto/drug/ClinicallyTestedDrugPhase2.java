package com.ftn.anticancerdrugrecord.dto.drug;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClinicallyTestedDrugPhase2 extends ClinicallyTestedDrugPhase1 {

    private boolean hasSideEffects;

    public ClinicallyTestedDrugPhase2(String type) {
        super(type);
        this.hasSideEffects = Boolean.FALSE;
    }
}
