package com.comeon.backend.user.api.v1;

import com.comeon.backend.common.jwt.JwtManager;
import com.comeon.backend.common.jwt.JwtToken;
import com.comeon.backend.user.command.application.dto.AppleOauthRequest;
import com.comeon.backend.user.command.application.OauthUserFacade;
import com.comeon.backend.user.command.application.dto.GoogleOauthRequest;
import com.comeon.backend.user.command.application.dto.KakaoOauthRequest;
import com.comeon.backend.user.query.UserDao;
import com.comeon.backend.user.api.v1.dto.UserTokenResponse;
import com.comeon.backend.user.query.UserSimple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/oauth")
public class OauthUserApiController {

    private final OauthUserFacade oauthUserFacade;
    private final UserDao userDao;
    private final JwtManager jwtManager;

    @PostMapping("/google")
    public UserTokenResponse googleOauthLogin(@Validated @RequestBody GoogleOauthRequest request) {
        Long userId = oauthUserFacade.googleLogin(request);
        return getResponse(userId);
    }

    private UserTokenResponse getResponse(Long userId) {
        UserSimple userSimple = userDao.findUserSimple(userId);

        JwtToken atk = jwtManager.buildAtk(
                userSimple.getUserId(),
                userSimple.getNickname(),
                userSimple.getRole()
        );
        JwtToken rtk = jwtManager.buildRtk(userSimple.getUserId());

        return UserTokenResponse.create(atk, rtk);
    }

    @PostMapping("/kakao")
    public UserTokenResponse kakaoOauthLogin(@Validated @RequestBody KakaoOauthRequest request) {
        Long userId = oauthUserFacade.kakaoLogin(request);
        return getResponse(userId);
    }

    @PostMapping("/apple")
    public UserTokenResponse appleOauthLogin(@Validated @RequestBody AppleOauthRequest request) {
        Long userId = oauthUserFacade.appleLogin(request);
        return getResponse(userId);
    }
}
