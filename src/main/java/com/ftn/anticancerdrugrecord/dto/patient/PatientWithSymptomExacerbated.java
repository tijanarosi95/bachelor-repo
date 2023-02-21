package com.ftn.anticancerdrugrecord.dto.patient;

import com.ftn.anticancerdrugrecord.model.person.LifeQuality;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientWithSymptomExacerbated extends PatientWithSymptom {

    private boolean strongPain;

    private boolean weightLoss;

    private LifeQuality lifeQuality;

    public PatientWithSymptomExacerbated() {
        super();
        this.lifeQuality = LifeQuality.Worse;
        this.strongPain = true;
        this.weightLoss = true;
    }
}
