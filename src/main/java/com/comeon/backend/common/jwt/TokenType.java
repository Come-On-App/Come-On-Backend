package com.comeon.backend.common.jwt;

public enum TokenType {

    ACCESS("access-token", "엑세스 토큰"),
    REFRESH("refresh-token", "리프레시 토큰"),
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
}
