package com.comeon.backend.user.command.domain;

public interface KakaoOauthService {

    OauthUserInfo getUserInfoByCode(String code);
}
