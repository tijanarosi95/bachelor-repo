package com.ftn.anticancerdrugrecord.dto.drug;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DrugEffectsDTO {

    private String drugId;

    private String drugName;

    private boolean isDoseRanged;

    private boolean hasEfficacy;

    private boolean hasToxicity;

    private boolean hasSideEffects;

    private boolean hasTherapeuticEffect;

    private boolean isApproved;
}
