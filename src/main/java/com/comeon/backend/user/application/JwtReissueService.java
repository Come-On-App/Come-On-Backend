package com.comeon.backend.user.application;

import com.comeon.backend.common.exception.RestApiException;
import com.comeon.backend.common.jwt.*;
import com.comeon.backend.user.common.UserErrorCode;
import com.comeon.backend.user.domain.User;
import com.comeon.backend.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtReissueService {

    private final JwtValidator jwtValidator;
    private final JwtRepository jwtRepository;
    private final JwtGenerator jwtGenerator;
    private final JwtParser jwtParser;
    private final UserRepository userRepository;

    public Tokens reissue(String refreshTokenValue, boolean reissueRtkAlways) {
        if (jwtValidator.valid(refreshTokenValue)) {
            Long userId = jwtRepository.getUserIdBy(refreshTokenValue).orElseThrow();
            User user = userRepository.findById(userId).orElseThrow();

            JwtToken accessToken = jwtGenerator.initBuilder(TokenType.ACCESS)
                    .userId(user.getId())
                    .authorities(user.getRole().getValue())
                    .nickname(user.getNickname())
                    .build();

            // RTK 재발급 조건을 만족하거나 파라미터가 true 이면 RTK 재발급
            JwtToken refreshToken = null;
            if (jwtParser.isRtkSatisfiedAutoReissueCondition(refreshTokenValue) || reissueRtkAlways) {
                // TODO JwtRepository 저장
                refreshToken = jwtGenerator.initBuilder(TokenType.REFRESH)
                        .build();
            }
            return new Tokens(accessToken, refreshToken);
        }
        throw new RestApiException("리프레시 토큰 검증 실패", UserErrorCode.INVALID_USER_RTK);
    }
}
