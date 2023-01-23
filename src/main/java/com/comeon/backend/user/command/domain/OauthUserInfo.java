package com.comeon.backend.user.command.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OauthUserInfo {

    private OauthProvider provider;
    private String oauthId;
    private String email;
    private String name;

    public static OauthUserInfo ofGoogle(String oauthId, String email, String name) {
        return new OauthUserInfo(OauthProvider.GOOGLE, oauthId, email, name);
    }

    public static OauthUserInfo ofKakao(String oauthId, String email, String name) {
        return new OauthUserInfo(OauthProvider.KAKAO, oauthId, email, name);
    }
}
