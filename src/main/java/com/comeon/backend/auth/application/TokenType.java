package com.comeon.backend.auth.application;

import java.util.Arrays;

public enum TokenType {

    ATK("access-token", "엑세스 토큰"),
    RTK("refresh-token", "리프레시 토큰"),
    ;

    private final String value;
    private final String description;

    TokenType(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static TokenType of(String name) {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("name param is empty");
        }

        return Arrays.stream(values())
                .filter(tokenType -> tokenType.name().equals(name.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(name + " type is invalid type"));
    }
}
