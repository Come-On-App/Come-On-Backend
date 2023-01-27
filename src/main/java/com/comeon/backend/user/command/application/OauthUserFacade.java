package com.comeon.backend.user.command.application;

import com.comeon.backend.user.command.application.dto.LoginDto;
import com.comeon.backend.user.command.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OauthUserFacade {

    private final GoogleOauthService googleOauthService;
    private final KakaoOauthService kakaoOauthService;
    private final OauthUserService oauthUserService;
    private final JwtService jwtService;

    public LoginDto.OauthLoginResponse googleLogin(LoginDto.GoogleOauthRequest request) {
        OauthUserInfo oauthUserInfo = googleOauthService.getUserInfoByIdToken(request.getIdToken());
        User user = oauthUserService.saveUser(oauthUserInfo);
        return jwtService.issueTokens(user.getId(), user.getNickname(), user.getRole().getValue());
    }

    public LoginDto.OauthLoginResponse kakaoLogin(LoginDto.KakaoOauthRequest request) {
        OauthUserInfo oauthUserInfo = kakaoOauthService.getUserInfoByCode(request.getCode());
        User user = oauthUserService.saveUser(oauthUserInfo);
        return jwtService.issueTokens(user.getId(), user.getNickname(), user.getRole().getValue());
    }
}
