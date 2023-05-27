package com.ftn.anticancerdrugrecord.model.drug;

import com.ftn.anticancerdrugrecord.model.disease.Disease;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Drug {

    private Integer drugId;

    private String name;

    private String activeIngredient;

    private boolean isDoseRanged;

    private boolean hasEfficacy;

    private boolean hasToxicity;

    private boolean hasSideEffects;

    private boolean hasTherapeuticEffect;

    private boolean isApproved;

    private Set<Disease> mayTreat;

    public Drug(String name) {
        this.name = name;
    }
}
