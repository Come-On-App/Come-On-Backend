package com.comeon.backend.user.command.domain;

public enum OauthProvider {

    KAKAO("카카오"),
    GOOGLE("구글"),
    APPLE("애플"),
    ;

    private final String name;

    OauthProvider(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
