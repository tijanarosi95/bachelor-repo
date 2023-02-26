package com.ftn.anticancerdrugrecord.dto.patient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientWithDiseaseProgression extends PatientWithDiseaseCourse {

    private boolean isCancerReappear;

    private boolean isCancerDetectable;

    public PatientWithDiseaseProgression() {
        super("PROGRESSION");
        this.isCancerReappear = false;
        this.isCancerDetectable = true;
    }
}
