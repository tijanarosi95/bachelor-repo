package com.ftn.anticancerdrugrecord.model.person;

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

    private boolean isCancerSpread;

    private boolean isCancerGrown;

    private boolean isCancerSpreadToOrgans;

    private boolean strongPain;

    private boolean isCancerReappear;

    private boolean isCancerDetectable;

    private Drug isTreatedWith;

    private Disease hasDisease;

    private LifeQuality lifeQuality;
}
