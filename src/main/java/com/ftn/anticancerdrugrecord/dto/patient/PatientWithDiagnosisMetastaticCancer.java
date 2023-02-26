package com.ftn.anticancerdrugrecord.dto.patient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientWithDiagnosisMetastaticCancer extends PatientWithDiagnosis {

    private boolean isCancerSpread;

    private boolean isCancerGrown;

    private boolean isCancerSpreadToOrgans;

    public PatientWithDiagnosisMetastaticCancer() {
        super("METASTATIC_CANCER");
        this.isCancerGrown = true;
        this.isCancerSpread = true;
        this.isCancerSpreadToOrgans = true;
    }
}
