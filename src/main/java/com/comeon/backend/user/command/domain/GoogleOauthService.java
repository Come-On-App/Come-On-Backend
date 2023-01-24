package com.comeon.backend.user.command.domain;

public interface GoogleOauthService {

    OauthUserInfo getUserInfoByIdToken(String idToken);
}
