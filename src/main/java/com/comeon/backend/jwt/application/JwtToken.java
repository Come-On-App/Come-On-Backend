package com.comeon.backend.jwt.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtToken {

    private String token;
    private Payload payload;
}
