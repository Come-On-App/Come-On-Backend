package com.comeon.backend.user.command.application;

import com.comeon.backend.common.jwt.JwtGenerator;
import com.comeon.backend.common.jwt.JwtToken;
import com.comeon.backend.common.jwt.RefreshTokenRepository;
import com.comeon.backend.common.jwt.TokenType;
import com.comeon.backend.user.command.domain.OauthProvider;
import com.comeon.backend.user.command.domain.User;
import com.comeon.backend.user.command.domain.SocialLoginHandler;
import com.comeon.backend.user.infrastructure.feign.kakao.KakaoApiFeignService;
import com.comeon.backend.user.infrastructure.feign.kakao.KakaoAuthFeignService;
import com.comeon.backend.user.infrastructure.feign.kakao.KakaoOAuthTokenResponse;
import com.comeon.backend.user.infrastructure.feign.kakao.KakaoUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class KakaoUserService {

    private final KakaoAuthFeignService kakaoAuthFeignService;
    private final KakaoApiFeignService kakaoApiFeignService;
    private final SocialLoginHandler socialLoginHandler;
    private final JwtGenerator jwtGenerator;
    private final RefreshTokenRepository refreshTokenRepository;

    public Tokens login(String code) {
        KakaoOAuthTokenResponse kakaoOAuthToken = kakaoAuthFeignService.getToken(code);
        KakaoUserInfoResponse kakaoUserInfo = kakaoApiFeignService.getUserInfo(kakaoOAuthToken.getAccessToken());

        User user = socialLoginHandler.doLogin(
                String.valueOf(kakaoUserInfo.getId()),
                OauthProvider.KAKAO,
                (String) kakaoUserInfo.getKakaoAccount().get("email"),
                (String) ((Map<String, Object>) kakaoUserInfo.getKakaoAccount().get("profile")).get("nickname")
        );

        JwtToken accessToken = jwtGenerator.initBuilder(TokenType.ACCESS)
                .userId(user.getId())
                .authorities(user.getRole().getValue())
                .nickname(user.getNickname())
                .build();

        JwtToken refreshToken = jwtGenerator.initBuilder(TokenType.REFRESH).build();
        refreshTokenRepository.add(refreshToken.getToken(), user.getId(), refreshToken.getClaims().getExpiration());

        return new Tokens(accessToken, refreshToken);
    }
}
