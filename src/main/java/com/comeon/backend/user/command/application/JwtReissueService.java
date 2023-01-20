package com.comeon.backend.user.command.application;

import com.comeon.backend.common.exception.RestApiException;
import com.comeon.backend.common.jwt.*;
import com.comeon.backend.user.command.domain.UserRepository;
import com.comeon.backend.user.common.UserErrorCode;
import com.comeon.backend.user.command.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtReissueService {

    private final JwtValidator jwtValidator;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtGenerator jwtGenerator;
    private final JwtParser jwtParser;
    private final UserRepository userRepository;

    public Tokens reissue(String refreshTokenValue, boolean reissueRtkAlways) {
        if (!jwtValidator.valid(refreshTokenValue)) {
            throw new RestApiException("리프레시 토큰 검증 실패", UserErrorCode.INVALID_USER_RTK);
        }

        Long userId = refreshTokenRepository.getUserIdBy(refreshTokenValue).orElseThrow();
        User user = userRepository.findBy(userId).orElseThrow();

        JwtToken accessToken = jwtGenerator.initBuilder(TokenType.ACCESS)
                .userId(user.getId())
                .authorities(user.getRole().getValue())
                .nickname(user.getNickname())
                .build();

        // RTK 재발급 조건을 만족하거나 파라미터가 true 이면 RTK 재발급
        JwtToken refreshToken = null;
        if (jwtParser.isRtkSatisfiedAutoReissueCondition(refreshTokenValue) || reissueRtkAlways) {
            refreshToken = jwtGenerator.initBuilder(TokenType.REFRESH).build();
            refreshTokenRepository.add(refreshToken.getToken(), user.getId(), refreshToken.getClaims().getExpiration());
        }

        return new Tokens(accessToken, refreshToken);
    }
}
