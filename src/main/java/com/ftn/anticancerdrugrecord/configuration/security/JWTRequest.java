package com.ftn.anticancerdrugrecord.configuration.security;

import java.io.Serializable;
import lombok.Data;

@Data
public class JWTRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String username;
    private String password;
}
