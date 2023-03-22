package com.comeon.backend.common.jwt;

import java.util.Map;

public interface JwtManager {

    Map<String, Object> parseToMap(String token);
    Payload parse(String token);

    boolean verifyAtk(String token);

    boolean verifyRtk(String token);

    JwtToken buildAtk(Long userId, String nickname, String authorities);

    JwtToken buildRtk(Long userId);

    Long getUserIdByRtk(String token);

    boolean isSatisfiedRtkReissueCond(String refreshToken);

    void logout(Long userId);
}
