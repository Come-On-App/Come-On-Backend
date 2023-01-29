package com.comeon.backend.user.infrastructure.command.domain.oauth;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class KakaoOauthProperties {

    private final String authorizationGrantType;
    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;

    public KakaoOauthProperties(@Value("${spring.security.oauth2.client.registration.kakao.authorization-grant-type}") String authorizationGrantType,
                                @Value("${spring.security.oauth2.client.registration.kakao.client-id}") String clientId,
                                @Value("${spring.security.oauth2.client.registration.kakao.client-secret}") String clientSecret,
                                @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}") String redirectUri) {
        this.authorizationGrantType = authorizationGrantType;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }
}
