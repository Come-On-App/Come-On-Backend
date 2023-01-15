package com.comeon.backend.common.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtToken {

    private String token;
    private JwtClaims claims;
}
