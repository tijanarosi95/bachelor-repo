package com.ftn.anticancerdrugrecord.dto.patient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientUpdateDTO {

    private PatientExistingDataDTO existingData;

    private PatientNewDataDTO newData;
}
