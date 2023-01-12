package com.comeon.backend.user.application;

import com.comeon.backend.common.jwt.JwtGenerator;
import com.comeon.backend.common.jwt.JwtToken;
import com.comeon.backend.common.jwt.TokenType;
import com.comeon.backend.user.domain.OauthProvider;
import com.comeon.backend.user.domain.User;
import com.comeon.backend.user.domain.UserService;
import com.comeon.backend.user.infrastructure.feign.google.GoogleAuthFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class GoogleAuthService {

    private final GoogleAuthFeignClient googleAuthFeignClient;
    private final UserService userService;
    private final JwtGenerator jwtGenerator;

    public Tokens login(String idToken) {
        Map<String, Object> payload = googleAuthFeignClient.verifyIdToken(idToken);

        User user = userService.saveUser(
                (String) payload.get("sub"),
                OauthProvider.GOOGLE,
                (String) payload.get("email"),
                (String) payload.get("name")
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
