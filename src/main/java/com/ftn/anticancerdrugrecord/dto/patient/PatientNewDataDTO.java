package com.ftn.anticancerdrugrecord.dto.patient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ftn.anticancerdrugrecord.dto.disease.DiseaseDTO;
import com.ftn.anticancerdrugrecord.dto.drug.DrugDTO;
import com.ftn.anticancerdrugrecord.model.person.LifeQuality;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientNewDataDTO {

    private int age;

    @JsonProperty(value = "isCancerSpread")
    private boolean isCancerSpread;

    @JsonProperty(value = "isCancerGrown")
    private boolean isCancerGrown;

    @JsonProperty(value = "isCancerSpreadToOrgans")
    private boolean isCancerSpreadToOrgans;

    @JsonProperty(value = "strongPain")
    private boolean strongPain;

    private boolean weightLoss;

    @JsonProperty(value = "isCancerReappear")
    private boolean isCancerReappear;

    @JsonProperty(value = "isCancerDetectable")
    private boolean isCancerDetectable;

    private DrugDTO isTreatedWith;

    private DiseaseDTO hasDisease;

    private LifeQuality lifeQuality;
}
