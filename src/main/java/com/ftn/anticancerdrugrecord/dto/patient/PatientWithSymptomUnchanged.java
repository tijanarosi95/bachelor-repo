package com.ftn.anticancerdrugrecord.dto.patient;

import com.ftn.anticancerdrugrecord.model.person.LifeQuality;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientWithSymptomUnchanged extends PatientWithSymptom {

    private boolean strongPain;

    private boolean weightLoss;

    private LifeQuality lifeQuality;

    public PatientWithSymptomUnchanged() {
        super();
        this.lifeQuality = LifeQuality.Same;
        this.strongPain = true;
        this.weightLoss = true;
    }
}
