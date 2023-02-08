package com.comeon.backend.common.jwt;

public interface JwtManager {

    Payload parse(String token);

    boolean verifyAtk(String token);

    boolean verifyRtk(String token);

    JwtToken buildAtk(Long userId, String nickname, String authorities);

    JwtToken buildRtk(Long userId);

    Long getUserIdByRtk(String token);

    boolean isSatisfiedRtkReissueCond(String refreshToken);
}
