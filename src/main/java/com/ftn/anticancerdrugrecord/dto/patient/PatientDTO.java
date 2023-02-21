package com.ftn.anticancerdrugrecord.dto.patient;

import com.ftn.anticancerdrugrecord.dto.disease.DiseaseDTO;
import com.ftn.anticancerdrugrecord.model.person.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PatientDTO {

    private String jmbg;

    private String firstName;

    private String lastName;

    private Gender gender;

    private int age;

    private PatientWithDiseaseCourse diseaseCourse;

    private PatientWithDiagnosis diagnosis;

    private PatientWithSymptom symptoms;

    private DiseaseDTO hasDisease;
}
