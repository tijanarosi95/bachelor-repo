package com.ftn.anticancerdrugrecord.dto.drug;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    private String activeIngredient;

    @JsonProperty(value = "isDoseRanged")
    private boolean isDoseRanged;

    @JsonProperty(value = "hasEfficacy")
    private boolean hasEfficacy;

    @JsonProperty(value = "hasToxicity")
    private boolean hasToxicity;

    @JsonProperty(value = "hasSideEffects")
    private boolean hasSideEffects;

    @JsonProperty(value = "hasTherapeuticEffect")
    private boolean hasTherapeuticEffect;

    @JsonProperty(value = "isApproved")
    private boolean isApproved;
}
