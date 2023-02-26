package com.ftn.anticancerdrugrecord.dto.drug;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Drug {

    private String type;

    public Drug(String type) {
        this.type = type;
    }
}
