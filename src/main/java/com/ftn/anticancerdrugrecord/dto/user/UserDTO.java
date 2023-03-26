package com.ftn.anticancerdrugrecord.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserDTO {

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    @JsonProperty(value = "role")
    private String userRole;
}
