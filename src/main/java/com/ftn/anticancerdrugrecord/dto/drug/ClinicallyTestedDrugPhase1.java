package com.ftn.anticancerdrugrecord.dto.drug;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClinicallyTestedDrugPhase1 extends PreclinicallyTestedDrug {

    private boolean isDoseRanged;

    public ClinicallyTestedDrugPhase1(String type) {
        super(type);
        this.isDoseRanged = Boolean.TRUE;
    }
}
