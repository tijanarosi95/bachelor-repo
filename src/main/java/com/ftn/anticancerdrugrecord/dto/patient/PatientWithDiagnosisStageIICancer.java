package com.ftn.anticancerdrugrecord.dto.patient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientWithDiagnosisStageIICancer extends PatientDTO {

    private boolean isCancerSpread;

    private boolean isCancerGrown;

    private boolean isCancerSpreadToOrgans;

    public PatientWithDiagnosisStageIICancer() {
        super();
        this.isCancerSpread = true;
        this.isCancerSpread = true;
        this.isCancerSpreadToOrgans = false;
    }
}
