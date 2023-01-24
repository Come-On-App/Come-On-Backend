package com.comeon.backend.jwt.application;

import com.comeon.backend.jwt.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtManager {

    private final JwtBuilder jwtBuilder;
    private final JwtParser jwtParser;
    private final JwtValidator jwtValidator;
    private final RefreshTokenRepository rtkRepository;

    public Payload parse(String token) {
        JwtClaims claims = jwtParser.parse(token);
        return new Payload(claims);
    }

    public boolean verifyAtk(String token) {
        return jwtValidator.verifyAtk(token);
    }

    public boolean verifyRtk(String token) {
        return jwtValidator.verifyRtk(token);
    }

    public Tokens buildTokens(Long userId, String nickname, String authorities) {
        JwtToken atk = buildAccessToken(userId, nickname, authorities);
        JwtToken rtk = buildRefreshToken(userId);
        return new Tokens(atk, rtk);
    }

    public JwtToken buildAccessToken(Long userId, String nickname, String authorities) {
        String atkValue = jwtBuilder.buildAtk(userId, nickname, authorities);
        Payload payload = new Payload(jwtParser.parse(atkValue));
        return new JwtToken(atkValue, payload);
    }

    public JwtToken buildRefreshToken(Long userId) {
        String rtkValue = jwtBuilder.buildRtk();
        Payload payload = new Payload(jwtParser.parse(rtkValue));
        JwtToken rtk = new JwtToken(rtkValue, payload);

        rtkRepository.add(rtk.getToken(), userId, rtk.getPayload().getExpiration());

        return rtk;
    }
}
