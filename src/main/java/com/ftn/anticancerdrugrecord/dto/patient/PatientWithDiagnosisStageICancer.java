package com.ftn.anticancerdrugrecord.dto.patient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientWithDiagnosisStageICancer extends PatientWithDiagnosis {

    private boolean isCancerSpread;

    private boolean isCancerGrown;

    private boolean isCancerSpreadToOrgans;

    public PatientWithDiagnosisStageICancer() {
        super();
        this.isCancerGrown = true;
        this.isCancerSpread = false;
        this.isCancerSpreadToOrgans = false;
    }
}
