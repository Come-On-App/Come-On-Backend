package com.comeon.backend.user.presentation.api.v1;

import com.comeon.backend.common.jwt.JwtManager;
import com.comeon.backend.config.security.JwtPrincipal;
import com.comeon.backend.user.command.application.UserFacade;
import com.comeon.backend.user.presentation.api.v1.dto.UserWithdrawResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/me")
public class UserWithdrawController {

    private final JwtManager jwtManager;
    private final UserFacade userFacade;

    @DeleteMapping
    public UserWithdrawResponse userWithdraw(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal
    ) {
        userFacade.withdrawUser(jwtPrincipal.getUserId());
        jwtManager.logout(jwtPrincipal.getUserId());
        return new UserWithdrawResponse();
    }
}
