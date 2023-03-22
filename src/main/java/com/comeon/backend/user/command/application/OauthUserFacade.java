package com.comeon.backend.user.command.application;

import com.comeon.backend.user.command.application.dto.AppleOauthRequest;
import com.comeon.backend.user.command.application.dto.GoogleOauthRequest;
import com.comeon.backend.user.command.application.dto.KakaoOauthRequest;
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
    private final AppleOauthService appleOauthService;
    private final OauthUserService oauthUserService;

    public Long googleLogin(GoogleOauthRequest request) {
        OauthUserInfo oauthUserInfo = googleOauthService.getUserInfoByIdToken(request.getIdToken());
        User user = saveUser(oauthUserInfo);
        return user.getId();
    }

    public Long kakaoLogin(KakaoOauthRequest request) {
        OauthUserInfo oauthUserInfo = kakaoOauthService.getUserInfoByCode(request.getCode());
        User user = saveUser(oauthUserInfo);
        return user.getId();
    }

    public Long appleLogin(AppleOauthRequest request) {
        OauthUserInfo oauthUserInfo = appleOauthService.getUserInfoBy(request.getIdentityToken(), request.getUser(), request.getEmail(), request.getName());
        User user = saveUser(oauthUserInfo);
        return user.getId();
    }

    private User saveUser(OauthUserInfo oauthUserInfo) {
        return oauthUserService.saveUser(oauthUserInfo);
    }
}
