package com.ftn.anticancerdrugrecord.dto.disease;

import com.ftn.anticancerdrugrecord.model.disease.Disease;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DiseaseDTO {

    private Integer id;

    private String name;

    public DiseaseDTO(Disease disease) {
        this.id = disease.getId();
        this.name = disease.getName();
    }
}
