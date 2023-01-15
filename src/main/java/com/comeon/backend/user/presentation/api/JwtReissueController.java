package com.comeon.backend.user.presentation.api;

import com.comeon.backend.user.application.JwtReissueService;
import com.comeon.backend.user.application.Tokens;
import com.comeon.backend.user.presentation.api.request.JwtReissueRequest;
import com.comeon.backend.user.presentation.api.response.AppAuthTokensResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/reissue")
public class JwtReissueController {

    private final JwtReissueService jwtReissueService;

    @PostMapping
    public AppAuthTokensResponse jwtReissue(@Validated @RequestBody JwtReissueRequest request) {
        Tokens tokens = jwtReissueService.reissue(
                request.getRefreshToken(),
                request.getReissueRefreshTokenAlways()
        );

        return AppAuthTokenResponseUtil.generateResponse(tokens);
    }
}
