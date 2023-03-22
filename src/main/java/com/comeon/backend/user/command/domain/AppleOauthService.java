package com.comeon.backend.user.command.domain;

public interface AppleOauthService {

    OauthUserInfo getUserInfoBy(String identityToken, String user, String email, String name);
}
