package com.ftn.anticancerdrugrecord.model.person;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ftn.anticancerdrugrecord.model.disease.Disease;
import com.ftn.anticancerdrugrecord.model.drug.Drug;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Person {

    private String jmbg;

    private String firstName;

    private String lastName;

    private Gender gender;

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

    private Drug isTreatedWith;

    private Disease hasDisease;

    private LifeQuality lifeQuality;

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public void setCancerSpread(boolean cancerSpread) {
        isCancerSpread = cancerSpread;
    }

    public void setCancerGrown(boolean cancerGrown) {
        isCancerGrown = cancerGrown;
    }

    public void setCancerSpreadToOrgans(boolean cancerSpreadToOrgans) {
        isCancerSpreadToOrgans = cancerSpreadToOrgans;
    }

    public void setCancerReappear(boolean cancerReappear) {
        isCancerReappear = cancerReappear;
    }

    public void setCancerDetectable(boolean cancerDetectable) {
        isCancerDetectable = cancerDetectable;
    }

    public void setIsTreatedWith(Drug isTreatedWith) {
        this.isTreatedWith = isTreatedWith;
    }
}
