package com.comeon.backend.common.jwt.infrastructure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClaimName {


    ISSUER("iss"),
    SUBJECT("sub"),
    EXPIRATION("exp"),
    ISSUED_AT("iat"),
    USER_ID("uid"),
    AUTHORITIES("aut"),
    NICKNAME("nik"),
    ;

    private final String value;
}
