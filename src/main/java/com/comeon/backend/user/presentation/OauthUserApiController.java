package com.comeon.backend.user.presentation;

import com.comeon.backend.user.command.application.OauthUserFacade;
import com.comeon.backend.user.command.application.dto.LoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/oauth")
public class OauthUserApiController {

    private final OauthUserFacade oauthUserFacade;

    @PostMapping("/google")
    public LoginDto.OauthLoginResponse googleOauthLogin(@Validated @RequestBody LoginDto.GoogleOauthRequest request) {
        return oauthUserFacade.googleLogin(request);
    }

    @PostMapping("/kakao")
    public LoginDto.OauthLoginResponse kakaoOauthLogin(@Validated @RequestBody LoginDto.KakaoOauthRequest request) {
         return oauthUserFacade.kakaoLogin(request);
    }
}
