package com.comeon.backend.user.application;

import com.comeon.backend.common.jwt.JwtToken;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Tokens {

    private JwtToken accessToken;
    private JwtToken refreshToken;
}
