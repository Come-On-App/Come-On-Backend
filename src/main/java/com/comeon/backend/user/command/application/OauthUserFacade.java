package com.comeon.backend.user.command.application;

import com.comeon.backend.user.command.application.dto.LoginUserDto;
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

    public LoginUserDto googleLogin(String idToken) {
        OauthUserInfo oauthUserInfo = googleOauthService.getUserInfoByIdToken(idToken);
        User user = oauthUserService.saveUser(oauthUserInfo);

        return new LoginUserDto(user);
    }

    public LoginUserDto kakaoLogin(String code) {
        OauthUserInfo oauthUserInfo = kakaoOauthService.getUserInfoByCode(code);
        User user = oauthUserService.saveUser(oauthUserInfo);

        return new LoginUserDto(user);
    }
}
