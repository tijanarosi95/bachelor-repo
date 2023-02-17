package com.ftn.anticancerdrugrecord.dto.patient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PatientDrugDTO {

    private String patientId;

    private String firstName;

    private String lastName;

    private String drugName;

    private String drugId;
}
