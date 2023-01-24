package com.comeon.backend.jwt.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Tokens {

    private JwtToken accessToken;
    private JwtToken refreshToken;
}
