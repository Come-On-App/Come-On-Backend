package com.comeon.backend.jwt.domain;

public interface JwtBuilder {

    String buildAtk(Long userId, String nickname, String authorities);
    String buildRtk();
}
