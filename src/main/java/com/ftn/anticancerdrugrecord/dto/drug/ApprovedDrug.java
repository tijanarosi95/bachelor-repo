package com.ftn.anticancerdrugrecord.dto.drug;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApprovedDrug extends ClinicallyTestedDrugPhase3 {

    private boolean isApproved;

    public ApprovedDrug() {
        super("APPROVED");
        this.isApproved = Boolean.TRUE;
    }
}
