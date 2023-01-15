package com.comeon.backend.user.presentation.api;

import com.comeon.backend.user.application.GoogleUserService;
import com.comeon.backend.user.application.KakaoUserService;
import com.comeon.backend.user.application.Tokens;
import com.comeon.backend.user.presentation.api.request.KakaoOAuth2LoginRequest;
import com.comeon.backend.user.presentation.api.request.GoogleOAuth2LoginRequest;
import com.comeon.backend.user.presentation.api.response.AppAuthTokensResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/oauth")
public class OAuth2LoginController {

    private final GoogleUserService googleUserService;
    private final KakaoUserService kakaoUserService;

    @PostMapping("/google")
    public AppAuthTokensResponse googleOAuth2Login(@Validated @RequestBody GoogleOAuth2LoginRequest request) {
        Tokens tokens = googleUserService.login(request.getIdToken());

        return AppAuthTokenResponseUtil.generateResponse(tokens);
    }

    @PostMapping("/kakao")
    public AppAuthTokensResponse kakaoOAuth2Login(@Validated @RequestBody KakaoOAuth2LoginRequest request) {
        Tokens tokens = kakaoUserService.login(request.getCode());

        return AppAuthTokenResponseUtil.generateResponse(tokens);
    }
}
