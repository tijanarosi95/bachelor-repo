package com.ftn.anticancerdrugrecord.dto.drug;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DrugUpdateDTO {

    private DrugEffectsDTO existingData;

    private DrugEffectsDTO newData;
}
