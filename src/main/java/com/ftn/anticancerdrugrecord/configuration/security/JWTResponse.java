package com.ftn.anticancerdrugrecord.configuration.security;

import java.io.Serializable;
import lombok.Data;

@Data
public class JWTResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwt;
}
