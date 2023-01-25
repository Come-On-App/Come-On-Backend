package com.comeon.backend.jwt.presentation;

import com.comeon.backend.jwt.application.JwtReissueFacade;
import com.comeon.backend.jwt.application.Tokens;
import com.comeon.backend.jwt.presentation.request.JwtReissueRequest;
import com.comeon.backend.jwt.presentation.response.JwtReissueResponse;
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
public class JwtReissueApiController {

    private final JwtReissueFacade jwtReissueFacade;

    @PostMapping
    public JwtReissueResponse jwtReissue(@Validated @RequestBody JwtReissueRequest request) {
        Tokens tokens = jwtReissueFacade.reissue(
                request.getRefreshToken(),
                request.getReissueRefreshTokenAlways()
        );

        return new JwtReissueResponse(tokens);
    }
}
