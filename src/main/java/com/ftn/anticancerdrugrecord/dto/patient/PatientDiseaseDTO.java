package com.ftn.anticancerdrugrecord.dto.patient;

import com.ftn.anticancerdrugrecord.dto.disease.DiseaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PatientDiseaseDTO {

    private String patientId;

    private String firstName;

    private String lastName;

    private DiseaseDTO disease;
}
