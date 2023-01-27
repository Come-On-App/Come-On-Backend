package com.comeon.backend.jwt.application;

import com.comeon.backend.common.error.RestApiException;
import com.comeon.backend.jwt.JwtErrorCode;
import com.comeon.backend.jwt.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class JwtReissueFacade {

    private final JwtBuilder jwtBuilder;
    private final JwtParser jwtParser;
    private final JwtValidator jwtValidator;
    private final ReissueCondition reissueCondition;
    private final RefreshTokenRepository rtkRepository;
    private final UserSimpleService userSimpleService;

    public Tokens reissue(String oldRtkValue, boolean rtkReissueCond) {
        if (!jwtValidator.verifyRtk(oldRtkValue)) {
            throw new RestApiException("리프레시 토큰 검증 실패", JwtErrorCode.INVALID_USER_RTK);
        }

        Long userId = rtkRepository.findUserIdBy(oldRtkValue).orElseThrow(
                () -> new RestApiException("세션에 해당 리프레시 토큰이 존재하지 않습니다.\nrequest rtk : " + oldRtkValue, JwtErrorCode.NO_SESSION)
        );
        UserSimple userSimple = userSimpleService.loadUserSimple(userId);

        String atkValue = jwtBuilder.buildAtk(
                userSimple.getUserId(),
                userSimple.getNickname(),
                userSimple.getAuthorities()
        );
        Payload atkPayload = new Payload(jwtParser.parse(atkValue));
        JwtToken atk = new JwtToken(atkValue, atkPayload);

        JwtToken rtk = generateRtk(oldRtkValue, userId, rtkReissueCond);

        return new Tokens(atk, rtk);
    }

    private JwtToken generateRtk(String oldRtkValue, Long userId, boolean rtkReissueCond) {
        JwtToken rtk = null;
        if (isRtkSatisfiedAutoReissueCondition(oldRtkValue) || rtkReissueCond) {
            String rtkValue = jwtBuilder.buildRtk();
            Payload rtkPayload = new Payload(jwtParser.parse(rtkValue));
            rtkRepository.add(rtkValue, userId, rtkPayload.getExpiration());
            rtk = new JwtToken(rtkValue, rtkPayload);
        }
        return rtk;
    }

    private boolean isRtkSatisfiedAutoReissueCondition(String rtk) {
        Instant expiration = jwtParser.parse(rtk).getExpiration();
        return reissueCondition.isSatisfied(expiration);
    }
}
