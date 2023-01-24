package com.comeon.backend.jwt.application;

import com.comeon.backend.common.exception.RestApiException;
import com.comeon.backend.jwt.common.JwtErrorCode;
import com.comeon.backend.jwt.domain.*;
import com.comeon.backend.user.command.domain.User;
import com.comeon.backend.user.command.domain.UserRepository;
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
    private final UserRepository userRepository;

    public Tokens reissue(String oldRtkValue, boolean rtkReissueCond) {
        if (!jwtValidator.verifyRtk(oldRtkValue)) {
            throw new RestApiException("리프레시 토큰 검증 실패", JwtErrorCode.INVALID_USER_RTK);
        }

        Long userId = rtkRepository.findUserIdBy(oldRtkValue).orElseThrow(
                () -> new RestApiException("세션에 해당 리프레시 토큰이 존재하지 않습니다.\nrequest rtk : " + oldRtkValue, JwtErrorCode.NO_SESSION)
        );
        // TODO 유저 조회 Dao
        User user = userRepository.findBy(userId).orElseThrow();
        String nickname = user.getNickname();
        String authorities = user.getRole().getValue();

        String atkValue = jwtBuilder.buildAtk(userId, nickname, authorities);
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
