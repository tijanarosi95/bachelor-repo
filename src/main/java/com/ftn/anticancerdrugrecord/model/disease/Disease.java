package com.ftn.anticancerdrugrecord.model.disease;

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
public class Disease {

    private int id;

    private String name;

    public Disease(String name) {
        this.name = name;
    }
}
