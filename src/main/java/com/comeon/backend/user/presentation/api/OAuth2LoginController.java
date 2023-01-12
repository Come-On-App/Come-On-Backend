package com.comeon.backend.user.presentation.api;

import com.comeon.backend.common.jwt.JwtClaims;
import com.comeon.backend.common.jwt.JwtParser;
import com.comeon.backend.user.application.GoogleAuthService;
import com.comeon.backend.user.application.KakaoAuthService;
import com.comeon.backend.user.application.Tokens;
import com.comeon.backend.user.presentation.api.request.KakaoOAuth2LoginRequest;
import com.comeon.backend.user.presentation.api.request.GoogleOAuth2LoginRequest;
import com.comeon.backend.user.presentation.api.response.AppAuthTokensResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/oauth")
public class OAuth2LoginController {

    private final JwtParser jwtParser;
    private final GoogleAuthService googleAuthService;
    private final KakaoAuthService kakaoAuthService;

    @PostMapping("/google")
    public AppAuthTokensResponse googleOAuth2Login(@RequestBody GoogleOAuth2LoginRequest request) {
        Tokens tokens = googleAuthService.login(request.getIdToken());

        return getAppAuthTokensResponse(tokens);
    }

    @PostMapping("/kakao")
    public AppAuthTokensResponse kakaoOAuth2Login(@RequestBody KakaoOAuth2LoginRequest request) {
        Tokens tokens = kakaoAuthService.login(request.getCode());

        return getAppAuthTokensResponse(tokens);
    }

    @NotNull
    private AppAuthTokensResponse getAppAuthTokensResponse(Tokens tokens) {
        String accessTokenValue = tokens.getAccessToken().getToken();
        JwtClaims accessTokenClaims = jwtParser.parse(accessTokenValue);
        long accTokenExpiry = accessTokenClaims.getExpiration().getEpochSecond();
        Long userId = accessTokenClaims.getUserId();

        String refreshTokenValue = tokens.getRefreshToken().getToken();
        long refTokenExpiry = jwtParser.parse(refreshTokenValue).getExpiration().getEpochSecond();

        return new AppAuthTokensResponse(
                new AppAuthTokensResponse.AccessToken(
                        accessTokenValue,
                        accTokenExpiry,
                        userId
                ),
                new AppAuthTokensResponse.RefreshToken(
                        refreshTokenValue,
                        refTokenExpiry
                )
        );
    }
}
