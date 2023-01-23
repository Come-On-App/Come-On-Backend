package com.comeon.backend.auth.domain;

public interface JwtBuilder {

    String buildAtk(Long userId, String nickname, String authorities);
    String buildRtk();
}
