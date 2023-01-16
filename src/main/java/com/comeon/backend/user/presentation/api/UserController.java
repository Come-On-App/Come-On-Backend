package com.comeon.backend.user.presentation.api;

import com.comeon.backend.common.security.JwtPrincipal;
import com.comeon.backend.user.application.UserDetails;
import com.comeon.backend.user.application.UserService;
import com.comeon.backend.user.presentation.api.response.UserDetailsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserDetailsResponse myDetails(@AuthenticationPrincipal JwtPrincipal jwtPrincipal) {
        UserDetails userDetails = userService.findUserDetails(jwtPrincipal.getUserId());

        return new UserDetailsResponse(
                userDetails.getUserId(),
                userDetails.getNickname(),
                userDetails.getProfileImageUrl(),
                userDetails.getRole(),
                userDetails.getEmail(),
                userDetails.getName()
        );
    }
}
