package com.ftn.anticancerdrugrecord.dto.patient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatientWithDiagnosis {

    private String type;

    public PatientWithDiagnosis(String type) {
        this.type = type;
    }
}
