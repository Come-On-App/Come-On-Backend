package com.comeon.backend.auth.application;

import com.comeon.backend.auth.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class JwtManager {

    private final JwtBuilder jwtBuilder;
    private final JwtParser jwtParser;
    private final JwtValidator jwtValidator;
    private final ReissueCondition reissueCondition;

    public Payload parse(String token) {
        JwtClaims claims = jwtParser.parse(token);
        return new Payload(claims);
    }

    public boolean isRtkSatisfiedAutoReissueCondition(String rtk) {
        Instant expiration = jwtParser.parse(rtk).getExpiration();
        return reissueCondition.isSatisfied(expiration);
    }

    public boolean verify(String token) {
        return jwtValidator.verify(token);
    }

    public JwtToken buildRefreshToken() {
        String rtk = jwtBuilder.buildRtk();
        Payload payload = new Payload(jwtParser.parse(rtk));
        return new JwtToken(rtk, payload);
    }

    public JwtToken buildAccessToken(Long userId, String nickname, String authorities) {
        String atk = jwtBuilder.buildAtk(userId, nickname, authorities);
        Payload payload = new Payload(jwtParser.parse(atk));
        return new JwtToken(atk, payload);
    }
}
