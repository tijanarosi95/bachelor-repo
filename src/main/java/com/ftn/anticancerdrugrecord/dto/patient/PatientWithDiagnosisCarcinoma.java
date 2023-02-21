package com.ftn.anticancerdrugrecord.dto.patient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientWithDiagnosisCarcinoma extends PatientWithDiagnosis {

    private boolean isCancerSpread;

    private boolean isCancerGrown;

    private boolean isCancerSpreadToOrgans;


    public PatientWithDiagnosisCarcinoma() {
        super();
        this.isCancerGrown = false;
        this.isCancerSpread = false;
        this.isCancerSpreadToOrgans = false;
    }
}
