package com.comeon.backend.user.presentation.api;

import com.comeon.backend.common.security.JwtPrincipal;
import com.comeon.backend.user.query.application.UserQueryService;
import com.comeon.backend.user.query.dto.UserDetails;
import com.comeon.backend.user.command.application.UserCommandService;
import com.comeon.backend.user.presentation.api.request.UserModifyRequest;
import com.comeon.backend.user.presentation.api.response.UserDetailsResponse;
import com.comeon.backend.user.presentation.api.response.UserModifyResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    @GetMapping("/me")
    public UserDetailsResponse myDetails(@AuthenticationPrincipal JwtPrincipal jwtPrincipal) {
        UserDetails userDetails = userQueryService.findUserDetails(jwtPrincipal.getUserId());

        return new UserDetailsResponse(
                userDetails.getUserId(),
                userDetails.getNickname(),
                userDetails.getProfileImageUrl(),
                userDetails.getRole(),
                userDetails.getEmail(),
                userDetails.getName()
        );
    }

    @PutMapping("/me")
    public UserModifyResponse modifyMe(@AuthenticationPrincipal JwtPrincipal jwtPrincipal,
                                       @RequestBody UserModifyRequest request) {
        userCommandService.modifyUser(jwtPrincipal.getUserId(), request.getNickname(), request.getProfileImageUrl());
        return new UserModifyResponse();
    }
}
