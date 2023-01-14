package com.comeon.backend.user.application;

import com.comeon.backend.common.jwt.JwtGenerator;
import com.comeon.backend.common.jwt.JwtToken;
import com.comeon.backend.common.jwt.TokenType;
import com.comeon.backend.user.domain.OauthProvider;
import com.comeon.backend.user.domain.User;
import com.comeon.backend.user.domain.SocialLoginHandler;
import com.comeon.backend.user.infrastructure.feign.google.GoogleAuthFeignService;
import com.comeon.backend.user.infrastructure.feign.google.GoogleUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GoogleUserService {

    private final GoogleAuthFeignService googleAuthFeignService;
    private final SocialLoginHandler socialLoginHandler;
    private final JwtGenerator jwtGenerator;

    public Tokens login(String idToken) {
        GoogleUserInfoResponse userInfo = googleAuthFeignService.getUserInfo(idToken);

        User user = socialLoginHandler.doLogin(
                userInfo.getOauthId(),
                OauthProvider.GOOGLE,
                userInfo.getEmail(),
                userInfo.getName()
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
