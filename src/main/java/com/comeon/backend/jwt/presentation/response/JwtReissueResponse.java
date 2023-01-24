package com.comeon.backend.jwt.presentation.response;

import com.comeon.backend.jwt.application.JwtToken;
import com.comeon.backend.jwt.application.Tokens;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class JwtReissueResponse {

    private AccessToken accessToken;
    private RefreshToken refreshToken;

    public JwtReissueResponse(Tokens tokens) {
        if (tokens.getAccessToken() != null) {
            this.accessToken = new AccessToken(tokens.getAccessToken());
        }
        if (tokens.getRefreshToken() != null) {
            this.refreshToken = new RefreshToken(tokens.getRefreshToken());
        }
    }

    @Getter
    @AllArgsConstructor
    public static class AccessToken {
        private String token;
        private Long expiry;
        private Long userId;

        public AccessToken(JwtToken jwtToken) {
            this.token = jwtToken.getToken();
            this.expiry = jwtToken.getPayload().getExpiration().toEpochMilli();
            this.userId = jwtToken.getPayload().getUserId();
        }
    }

    @Getter
    @AllArgsConstructor
    public static class RefreshToken {
        private String token;
        private Long expiry;

        public RefreshToken(JwtToken jwtToken) {
            this.token = jwtToken.getToken();
            this.expiry = jwtToken.getPayload().getExpiration().toEpochMilli();
        }
    }
}
