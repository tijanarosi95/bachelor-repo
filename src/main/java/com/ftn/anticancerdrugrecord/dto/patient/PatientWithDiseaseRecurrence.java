package com.ftn.anticancerdrugrecord.dto.patient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientWithDiseaseRecurrence extends PatientWithDiseaseCourse {

    private boolean isCancerReappear;

    private boolean isCancerDetectable;

    public PatientWithDiseaseRecurrence() {
        super("RECURRENCE");
        this.isCancerReappear = true;
        this.isCancerDetectable = true;
    }
}
