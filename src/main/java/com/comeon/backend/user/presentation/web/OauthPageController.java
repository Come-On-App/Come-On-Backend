package com.comeon.backend.user.presentation.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class OauthPageController {

    @GetMapping("/oauth/callback/kakao")
    public String kakaoOauthPage() {
        return "oauth-callback";
    }
}
