package com.comeon.backend.user.presentation.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class OauthPageController {

    @GetMapping("/oauth/callback/kakao")
    public String kakaoOauthPage(@RequestParam String code) {
        log.debug("code: {}", code);
//        String redirectUrl = "exp://127.0.0.1:19000/oauth/callback/kakao?code=" + code;
        String redirectUrl = "exp://192.168.1.3:19000/--/oauth/callback/kakao?code=" + code;
        log.debug("redirectUrl: {}", redirectUrl);

        return "redirect:" + redirectUrl;
    }
}
