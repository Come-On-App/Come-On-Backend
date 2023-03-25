package com.comeon.backend.user.presentation.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
public class OauthPageController {

    @GetMapping("/oauth/callback/kakao")
    public String kakaoOauthPage(HttpServletResponse response) {
        ResponseCookie responseCookie = ResponseCookie.from("_kawit", "")
                .maxAge(0)
                .domain(".kakao.com")
                .path("/")
                .secure(true)
                .sameSite("None")
                .build();
        response.addHeader("Set-Cookie", responseCookie.toString());

        return "oauth-callback";
    }
}
