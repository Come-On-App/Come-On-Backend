package com.comeon.backend.user.command.domain;

public enum OauthProvider {

    KAKAO("카카오"),
    GOOGLE("구글"),
    APPLE("애플"),
    ;

    private final String description;

    OauthProvider(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
