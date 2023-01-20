package com.comeon.backend.user.command.domain;

public enum Role {

    USER("ROLE_USER", "일반 유저 권한"),
    ADMIN("ROLE_ADMIN", "관리자 권한"),
    ;

    private final String value;
    private final String description;

    Role(String value, String description) {
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
