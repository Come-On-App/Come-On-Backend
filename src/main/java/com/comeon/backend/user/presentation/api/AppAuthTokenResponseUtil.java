package com.comeon.backend.user.presentation.api;

import com.comeon.backend.common.jwt.JwtToken;
import com.comeon.backend.user.application.Tokens;
import com.comeon.backend.user.presentation.api.response.AppAuthTokensResponse;

public class AppAuthTokenResponseUtil {

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
}
