package com.comeon.backend.common.jwt;

import java.time.Instant;

public abstract class JwtBuilder {

    protected String secretKey;
    protected JwtClaims claims;

    public JwtBuilder(String secretKey) {
        this.secretKey = secretKey;
        this.claims = new JwtClaims();
    }

    public JwtBuilder(String secretKey, JwtClaims claims) {
        this.secretKey = secretKey;
        this.claims = claims;
    }

    public JwtBuilder claim(String name, Object value) {
        this.claims.put(name, value);
        return this;
    }

    public JwtBuilder issuedAt(Instant iat) {
        this.claims.setIssuedAt(iat);
        return this;
    }

    public JwtBuilder expiration(Instant exp) {
        this.claims.setExpiration(exp);
        return this;
    }

    public JwtBuilder subject(String sub) {
        this.claims.setSubject(sub);
        return this;
    }

    public JwtBuilder userId(Long uid) {
        this.claims.setUserId(uid);
        return this;
    }

    public JwtBuilder issuer(String iss) {
        this.claims.setIssuer(iss);
        return this;
    }

    public JwtBuilder authorities(String auth) {
        this.claims.setAuthorities(auth);
        return this;
    }

    public JwtBuilder nickname(String nickname) {
        this.claims.setNickname(nickname);
        return this;
    }

    public abstract JwtToken build();
}
