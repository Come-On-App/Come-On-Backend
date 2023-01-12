package com.comeon.backend.user.domain;

public enum UserStatus {

    ACTIVATE("활성화 된 유저"),
    WITHDRAWN("탈퇴한 유저"),
    DISABLED("비활성화 된 유저"),
    ;

    private final String description;

    UserStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
