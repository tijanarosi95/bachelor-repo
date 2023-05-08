package com.ftn.anticancerdrugrecord.dto.disease;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiseaseUpdateDTO {

    private DiseaseDTO existingData;

    private DiseaseDTO newData;
}
