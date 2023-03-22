package com.comeon.backend.common.jwt.infrastructure;

import com.comeon.backend.common.jwt.JwtManager;
import com.comeon.backend.common.jwt.JwtClaims;
import com.comeon.backend.common.jwt.JwtToken;
import com.comeon.backend.common.jwt.Payload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtManagerImpl implements JwtManager {

    private final JwtBuilder jwtBuilder;
    private final JwtParser jwtParser;
    private final JwtValidator jwtValidator;
    private final ReissueCondition reissueCondition;
    private final RefreshTokenRepository rtkRepository;


    @Override
    public Map<String, Object> parseToMap(String token) {
        return jwtParser.parseToMap(token);
    }

    @Override
    public Payload parse(String token) {
        JwtClaims claims = jwtParser.parse(token);
        return new Payload(claims);
    }

    @Override
    public boolean verifyAtk(String token) {
        return jwtValidator.verifyAtk(token);
    }

    @Override
    public boolean verifyRtk(String token) {
        return jwtValidator.verifyRtk(token);
    }

    @Override
    public JwtToken buildAtk(Long userId, String nickname, String authorities) {
        String atkValue = jwtBuilder.buildAtk(userId, nickname, authorities);
        Payload payload = new Payload(jwtParser.parse(atkValue));
        return new JwtToken(atkValue, payload);
    }

    @Override
    public JwtToken buildRtk(Long userId) {
        String rtkValue = jwtBuilder.buildRtk(userId);
        Payload payload = new Payload(jwtParser.parse(rtkValue));
        JwtToken rtk = new JwtToken(rtkValue, payload);

        rtkRepository.add(userId, rtk.getToken(), rtk.getPayload().getExpiration());

        return rtk;
    }

    @Override
    public Long getUserIdByRtk(String token) {
        if (!jwtValidator.verifyRtk(token)) {
            throw new InvalidRtkException();
        }

        Long userId = jwtParser.parse(token).getUserId();
        String sessionTokenValue = rtkRepository.findRtkValueByUserId(userId)
                .orElseThrow(() -> generateNoSessionRtkException(token));

        if (!token.equals(sessionTokenValue)) {
            throw new RtkNotMatchException();
        }

        return userId;
    }

    private NoSessionRtkException generateNoSessionRtkException(String token) {
        return new NoSessionRtkException("세션에 해당 리프레시 토큰이 존재하지 않습니다.\nrequest rtk : " + token);
    }

    @Override
    public boolean isSatisfiedRtkReissueCond(String refreshToken) {
        Instant expiration = jwtParser.parse(refreshToken).getExpiration();
        return reissueCondition.isSatisfied(expiration);
    }

    @Override
    public void logout(Long userId) {
        rtkRepository.findRtkValueByUserId(userId).orElseThrow(NoSessionRtkException::new);
        rtkRepository.remove(userId);
    }
}
