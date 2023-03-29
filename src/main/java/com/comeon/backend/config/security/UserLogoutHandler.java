package com.comeon.backend.config.security;

import com.comeon.backend.common.jwt.JwtManager;
import com.comeon.backend.common.utils.SerializeUtils;
import com.comeon.backend.user.command.domain.OauthProvider;
import com.comeon.backend.user.presentation.web.LogoutRequest;
import com.comeon.backend.user.query.UserDao;
import com.comeon.backend.user.query.UserOauthInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.Cookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserLogoutHandler implements LogoutHandler {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;

    @Value("${spring.security.oauth2.logout.kakao.uri}")
    private String kakaoLogoutUri;

    @Value("${spring.security.oauth2.logout.callback-uri}")
    private String logoutCallbackUri;

    @Value("${cookie.secure}")
    private Boolean secureCookie;

    @Value("${cookie.same-site}")
    private String sameSiteCooke;

    private final JwtManager jwtManager;
    private final UserDao userDao;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String atk = request.getParameter("atk");
        String frontRedirectUri = request.getParameter("redirect_uri");

        // add logoutRequest Cookie
        ResponseCookie logoutCookie = ResponseCookie.from("logoutRequest", SerializeUtils.serialize(new LogoutRequest(atk, frontRedirectUri)))
                .path("/")
                .maxAge(60)
                .httpOnly(true)
                .secure(secureCookie)
                .sameSite(sameSiteCooke)
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, logoutCookie.toString());

        Long userId = jwtManager.parse(atk).getUserId();
        UserOauthInfo userOauthInfo = userDao.findUserOauthInfo(userId);

        String redirectLocation = null;
        if (OauthProvider.KAKAO.equals(userOauthInfo.getProvider())) {
            // kakao logout page redirect
            redirectLocation = UriComponentsBuilder.fromUriString(kakaoLogoutUri)
                    .queryParam("client_id", kakaoClientId)
                    .queryParam("logout_redirect_uri", logoutCallbackUri)
                    .build()
                    .toUriString();
        } else {
            // app logout page redirect
            redirectLocation = UriComponentsBuilder.fromUriString(logoutCallbackUri)
                    .build()
                    .toUriString();
        }

        try {
            response.sendRedirect(redirectLocation);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
