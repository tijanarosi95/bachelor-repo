package com.ftn.anticancerdrugrecord.dto.drug;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClinicallyTestedDrugPhase3 extends ClinicallyTestedDrugPhase2 {

    private boolean hasTherapeuticEffect;

    public ClinicallyTestedDrugPhase3(String type) {
        super(type);
        this.hasTherapeuticEffect = Boolean.TRUE;
    }
}
