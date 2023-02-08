package com.comeon.backend.common.jwt.infrastructure;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class JwtProperties {

    private final String secretKey;
    private final long rtkReissueCriteria;
    private final long accessTokenExpirySec;
    private final long refreshTokenExpirySec;

    public JwtProperties(@Value("${jwt.secret}") String secretKey,
                         @Value("${jwt.refresh-token.reissue-criteria}") long rtkReissueCriteria,
                         @Value("${jwt.access-token.expire-time}") long accessTokenExpirySec,
                         @Value("${jwt.refresh-token.expire-time}") long refreshTokenExpirySec) {
        this.secretKey = secretKey;
        this.rtkReissueCriteria = rtkReissueCriteria;
        this.accessTokenExpirySec = accessTokenExpirySec;
        this.refreshTokenExpirySec = refreshTokenExpirySec;
    }
}
