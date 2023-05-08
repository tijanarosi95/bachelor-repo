package com.ftn.anticancerdrugrecord.dto.drug;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ftn.anticancerdrugrecord.model.drug.Drug;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DrugEffectsDTO {

    private Integer drugId;

    private String name;

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

    public DrugEffectsDTO(final Drug drug) {
        this.drugId = drug.getDrugId();
        this.activeIngredient = drug.getActiveIngredient();
        this.name = drug.getName();
        this.isDoseRanged = drug.isDoseRanged();
        this.hasEfficacy = drug.isHasEfficacy();
        this.hasToxicity = drug.isHasToxicity();
        this.hasSideEffects = drug.isHasSideEffects();
        this.hasTherapeuticEffect = drug.isHasTherapeuticEffect();
        this.isApproved = drug.isApproved();
    }
}
