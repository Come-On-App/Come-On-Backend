package com.comeon.backend.user.presentation.web;

import com.comeon.backend.common.jwt.JwtManager;
import com.comeon.backend.common.utils.SerializeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LogoutPageController {

    @Value("${cookie.secure}")
    private Boolean secureCookie;

    @Value("${cookie.same-site}")
    private String sameSiteCooke;

    private final JwtManager jwtManager;

    @GetMapping("/logout/callback")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie logoutRequestCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> "logoutRequest".equals(cookie.getName()))
                .findFirst()
                .orElseThrow();

        // remove logoutRequest cookie
        ResponseCookie logoutCookie = ResponseCookie.from("logoutRequest", "")
                .path("/")
                .maxAge(0)
                .httpOnly(true)
                .secure(secureCookie)
                .sameSite(sameSiteCooke)
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, logoutCookie.toString());

        LogoutRequest logoutRequest = SerializeUtils.deserialize(logoutRequestCookie.getValue(), LogoutRequest.class);

        Long userId = jwtManager.parse(logoutRequest.getAtk()).getUserId();
        jwtManager.logout(userId);
        log.debug("user(id: {}) logout success..", userId);

        return "redirect:" + logoutRequest.getRedirectUri();
    }
}
