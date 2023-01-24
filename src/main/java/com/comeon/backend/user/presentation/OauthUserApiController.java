package com.comeon.backend.user.presentation;

import com.comeon.backend.jwt.application.JwtManager;
import com.comeon.backend.jwt.application.Tokens;
import com.comeon.backend.user.command.application.OauthUserFacade;
import com.comeon.backend.user.command.application.dto.LoginUserDto;
import com.comeon.backend.user.presentation.request.GoogleOauthRequest;
import com.comeon.backend.user.presentation.request.KakaoOauthRequest;
import com.comeon.backend.user.presentation.response.OauthLoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/oauth")
public class OauthUserApiController {

    private final JwtManager jwtManager;
    private final OauthUserFacade oauthUserFacade;

    @PostMapping("/google")
    public OauthLoginResponse googleOauthLogin(@Validated @RequestBody GoogleOauthRequest request) {
        LoginUserDto loginUserDto = oauthUserFacade.googleLogin(request.getIdToken());
        Tokens tokens = jwtManager.buildTokens(
                loginUserDto.getUserId(),
                loginUserDto.getNickname(),
                loginUserDto.getAuthorities()
        );

        return new OauthLoginResponse(tokens);
    }

    @PostMapping("/kakao")
    public OauthLoginResponse kakaoOauthLogin(@Validated @RequestBody KakaoOauthRequest request) {
        LoginUserDto loginUserDto = oauthUserFacade.kakaoLogin(request.getCode());
        Tokens tokens = jwtManager.buildTokens(
                loginUserDto.getUserId(),
                loginUserDto.getNickname(),
                loginUserDto.getAuthorities()
        );

        return new OauthLoginResponse(tokens);
    }
}
