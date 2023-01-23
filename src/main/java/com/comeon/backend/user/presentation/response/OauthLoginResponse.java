package com.comeon.backend.user.presentation.response;

import com.comeon.backend.jwt.application.JwtToken;
import com.comeon.backend.jwt.application.Tokens;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OauthLoginResponse {

    private AccessToken accessToken;
    private RefreshToken refreshToken;

    public OauthLoginResponse(Tokens tokens) {
        this.accessToken = new AccessToken(tokens.getAccessToken());
        this.refreshToken = new RefreshToken(tokens.getRefreshToken());
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
