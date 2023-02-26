package com.ftn.anticancerdrugrecord.dto.patient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatientWithSymptom {

    private String type;

    public PatientWithSymptom(String type) {
        this.type = type;
    }
}
