package com.comeon.backend.user.presentation.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class OauthPageController {

    @Value("${spring.security.oauth2.login.callback-uri}")
    private String callbackUri;

    @GetMapping("/oauth/callback/kakao")
    public String kakaoOauthPage(@RequestParam String code) {
        String redirectUrl = callbackUri + "?code" + code;
        log.debug("redirectUrl: {}", redirectUrl);

        return "redirect:" + redirectUrl;
    }
}
