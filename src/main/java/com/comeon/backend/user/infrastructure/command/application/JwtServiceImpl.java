package com.comeon.backend.user.infrastructure.command.application;

import com.comeon.backend.jwt.application.JwtManager;
import com.comeon.backend.jwt.application.JwtToken;
import com.comeon.backend.jwt.application.Tokens;
import com.comeon.backend.user.command.application.JwtService;
import com.comeon.backend.user.command.application.dto.LoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtManager jwtManager;

    @Override
    public LoginDto.OauthLoginResponse issueTokens(Long userId, String nickname, String authorities) {
        Tokens tokens = jwtManager.buildTokens(userId, nickname, authorities);
        JwtToken atk = tokens.getAccessToken();
        JwtToken rtk = tokens.getRefreshToken();

        return new LoginDto.OauthLoginResponse(
                new LoginDto.AccessTokenResponse(
                        atk.getToken(),
                        atk.getPayload().getExpiration().toEpochMilli(),
                        atk.getPayload().getUserId()
                ),
                new LoginDto.RefreshTokenResponse(
                        rtk.getToken(),
                        rtk.getPayload().getExpiration().toEpochMilli()
                )
        );
    }
}
