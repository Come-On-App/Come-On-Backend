package com.comeon.backend.user.presentation.api.v1;

import com.comeon.backend.common.jwt.JwtManager;
import com.comeon.backend.config.security.JwtPrincipal;
import com.comeon.backend.user.presentation.api.v1.dto.UserLogoutResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/logout")
public class UserLogoutController {

    private final JwtManager jwtManager;

    @PostMapping
    public UserLogoutResponse logout(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal
    ) {
        // 로그아웃 진행 (리프레시 토큰 삭제)
        jwtManager.logout(jwtPrincipal.getUserId());
        return new UserLogoutResponse();
    }
}
