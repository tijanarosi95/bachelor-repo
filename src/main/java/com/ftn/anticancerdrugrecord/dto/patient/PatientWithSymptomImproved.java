package com.ftn.anticancerdrugrecord.dto.patient;

import com.ftn.anticancerdrugrecord.model.person.LifeQuality;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientWithSymptomImproved extends PatientWithSymptom {

    private boolean strongPain;

    private boolean weightLoss;

    private LifeQuality lifeQuality;

    public PatientWithSymptomImproved() {
        super("IMPROVED");
        this.lifeQuality = LifeQuality.MuchBetter;
        this.strongPain = false;
        this.weightLoss = true;
    }
}
