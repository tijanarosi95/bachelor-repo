package com.ftn.anticancerdrugrecord.dto.patient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatientWithDiseaseCourse {

    private String type;

    public PatientWithDiseaseCourse(String type) {
        this.type = type;
    }
}
