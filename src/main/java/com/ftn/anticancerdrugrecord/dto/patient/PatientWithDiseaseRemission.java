package com.ftn.anticancerdrugrecord.dto.patient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientWithDiseaseRemission extends PatientWithDiseaseCourse {

    private boolean isCancerReappear;

    private boolean isCancerDetectable;

    public PatientWithDiseaseRemission() {
        super("REMISSION");
        this.isCancerDetectable = false;
        this.isCancerReappear = false;
    }
}
