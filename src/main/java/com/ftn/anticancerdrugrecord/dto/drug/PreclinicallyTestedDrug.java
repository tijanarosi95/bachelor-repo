package com.ftn.anticancerdrugrecord.dto.drug;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PreclinicallyTestedDrug extends Drug {

    private boolean hasEfficacy;

    private boolean hasToxicity;

    public PreclinicallyTestedDrug(String type) {
        super(type);
        this.hasEfficacy = Boolean.TRUE;
        this.hasToxicity = Boolean.FALSE;
    }
}
