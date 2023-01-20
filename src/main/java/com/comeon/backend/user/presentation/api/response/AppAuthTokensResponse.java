package com.comeon.backend.user.presentation.api.response;

import com.comeon.backend.common.jwt.JwtToken;
import com.comeon.backend.user.command.application.Tokens;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AppAuthTokensResponse {

    private AccessToken accessToken;
    private RefreshToken refreshToken;

    public static AppAuthTokensResponse generateResponse(Tokens tokens) {
        JwtToken accessToken = tokens.getAccessToken();
        AppAuthTokensResponse.AccessToken accessTokenResponse = null;
        if (accessToken != null) {
            accessTokenResponse = new AppAuthTokensResponse.AccessToken(
                    accessToken.getToken(),
                    accessToken.getClaims().getExpiration().getEpochSecond(),
                    accessToken.getClaims().getUserId()
            );
        }

        JwtToken refreshToken = tokens.getRefreshToken();
        AppAuthTokensResponse.RefreshToken refreshTokenResponse = null;
        if (refreshToken != null) {
            refreshTokenResponse = new AppAuthTokensResponse.RefreshToken(
                    refreshToken.getToken(),
                    refreshToken.getClaims().getExpiration().getEpochSecond()
            );
        }

        return new AppAuthTokensResponse(accessTokenResponse, refreshTokenResponse);
    }

    @Getter
    @AllArgsConstructor
    public static class AccessToken {
        private String token;
        private Long expiry;
        private Long userId;
    }

    @Getter
    @AllArgsConstructor
    public static class RefreshToken {
        private String token;
        private Long expiry;
    }
}
