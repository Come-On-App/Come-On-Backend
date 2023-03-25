package com.comeon.backend.user.presentation.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
public class OauthPageController {

    @GetMapping("/oauth/callback/kakao")
    public String kakaoOauthPage(@RequestParam String code,
                                 HttpServletResponse response) throws IOException {
        log.debug("code: {}", code);
        String redirectUrl = "come-on://oauth/callback/kakao?code=" + code;
        log.debug("redirectUrl: {}", redirectUrl);

        return "redirect:" + redirectUrl;
    }
}
