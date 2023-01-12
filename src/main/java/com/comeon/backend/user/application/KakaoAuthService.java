package com.comeon.backend.user.application;

import com.comeon.backend.common.jwt.JwtGenerator;
import com.comeon.backend.common.jwt.JwtToken;
import com.comeon.backend.common.jwt.TokenType;
import com.comeon.backend.user.domain.OauthProvider;
import com.comeon.backend.user.domain.User;
import com.comeon.backend.user.domain.UserService;
import com.comeon.backend.user.infrastructure.feign.kakao.KakaoApiFeignClient;
import com.comeon.backend.user.infrastructure.feign.kakao.KakaoAuthFeignClient;
import com.comeon.backend.user.infrastructure.feign.kakao.KakaoOAuthTokenResponse;
import com.comeon.backend.user.infrastructure.feign.kakao.KakaoUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class KakaoAuthService {

    private static final String BEARER_TYPE = "Bearer ";

    @Value("${spring.security.oauth2.client.registration.kakao.authorization-grant-type}")
    private String kakaoAuthorizationGrantType;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String kakaoClientSecret;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri;

    private final KakaoApiFeignClient kakaoApiFeignClient;
    private final KakaoAuthFeignClient kakaoAuthFeignClient;
    private final UserService userService;
    private final JwtGenerator jwtGenerator;

    public Tokens login(String code) {
        KakaoOAuthTokenResponse kakaoOAuthToken = kakaoAuthFeignClient.getToken(
                kakaoAuthorizationGrantType,
                kakaoClientId,
                kakaoClientSecret,
                kakaoRedirectUri,
                code
        );
        KakaoUserInfoResponse kakaoUserInfo = kakaoApiFeignClient.getUserInfo(
                BEARER_TYPE + kakaoOAuthToken.getAccessToken(),
                false,
                null
        );

        User user = userService.saveUser(
                String.valueOf(kakaoUserInfo.getId()),
                OauthProvider.KAKAO,
                (String) kakaoUserInfo.getKakaoAccount().get("name"),
                (String) ((Map<String, Object>) kakaoUserInfo.getKakaoAccount().get("profile")).get("nickname")
        );

        JwtToken accessToken = jwtGenerator.initBuilder(TokenType.ACCESS)
                .userId(user.getId())
                .authorities(user.getRole().getValue())
                .nickname(user.getNickname())
                .build();
        JwtToken refreshToken = jwtGenerator.initBuilder(TokenType.REFRESH)
                .build();

        return new Tokens(accessToken, refreshToken);
    }
}
