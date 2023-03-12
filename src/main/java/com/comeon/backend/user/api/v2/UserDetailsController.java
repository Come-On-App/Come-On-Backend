package com.comeon.backend.user.api.v2;

import com.comeon.backend.config.security.JwtPrincipal;
import com.comeon.backend.user.query.UserDetailsResponse;
import com.comeon.backend.user.query.UserQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/users")
public class UserDetailsController {

    private final UserQueryService userQueryService;

    @GetMapping("/me")
    public UserDetailsResponse myDetails(@AuthenticationPrincipal JwtPrincipal jwtPrincipal) {
        return userQueryService.findUserDetails(jwtPrincipal.getUserId());
    }
}
