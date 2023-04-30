package com.ftn.anticancerdrugrecord.dto.patient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientWithDiagnosisStageIICancer extends PatientWithDiagnosis {

    private boolean isCancerSpread;

    private boolean isCancerGrown;

    private boolean isCancerSpreadToOrgans;

    public PatientWithDiagnosisStageIICancer() {
        super("STAGE_II_CANCER");
        this.isCancerSpread = true;
        this.isCancerGrown = true;
        this.isCancerSpreadToOrgans = false;
    }
}
