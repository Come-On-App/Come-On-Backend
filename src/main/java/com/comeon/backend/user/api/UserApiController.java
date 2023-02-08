package com.comeon.backend.user.api;

import com.comeon.backend.config.security.JwtPrincipal;
import com.comeon.backend.user.api.dto.UserModifyResponse;
import com.comeon.backend.user.command.application.UserFacade;
import com.comeon.backend.user.command.application.dto.UserModifyRequest;
import com.comeon.backend.user.query.UserDao;
import com.comeon.backend.user.query.UserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserApiController {

    private final UserFacade userFacade;
    private final UserDao userDao;

    @GetMapping("/me")
    public UserDetails myDetails(@AuthenticationPrincipal JwtPrincipal jwtPrincipal) {
        return userDao.findUserDetails(jwtPrincipal.getUserId());
    }

    @PutMapping("/me")
    public UserModifyResponse modifyMe(@AuthenticationPrincipal JwtPrincipal jwtPrincipal,
                                       @Validated @RequestBody UserModifyRequest request) {
        userFacade.modifyUser(jwtPrincipal.getUserId(), request);
        return new UserModifyResponse();
    }
}
