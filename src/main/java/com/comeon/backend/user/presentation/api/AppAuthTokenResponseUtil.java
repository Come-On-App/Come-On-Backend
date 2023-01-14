package com.comeon.backend.user.presentation.api;

import com.comeon.backend.common.jwt.JwtClaims;
import com.comeon.backend.common.jwt.JwtParser;
import com.comeon.backend.user.application.Tokens;
import com.comeon.backend.user.presentation.api.response.AppAuthTokensResponse;

public class AppAuthTokenResponseUtil {

    public static AppAuthTokensResponse generateResponse(JwtParser jwtParser, Tokens tokens) {
        String accessTokenValue = tokens.getAccessToken().getToken();
        JwtClaims accessTokenClaims = jwtParser.parse(accessTokenValue);
        long accTokenExpiry = accessTokenClaims.getExpiration().getEpochSecond();
        Long userId = accessTokenClaims.getUserId();

        String refreshTokenValue = tokens.getRefreshToken().getToken();
        long refTokenExpiry = jwtParser.parse(refreshTokenValue).getExpiration().getEpochSecond();

        return new AppAuthTokensResponse(
                new AppAuthTokensResponse.AccessToken(
                        accessTokenValue,
                        accTokenExpiry,
                        userId
                ),
                new AppAuthTokensResponse.RefreshToken(
                        refreshTokenValue,
                        refTokenExpiry
                )
        );
    }
}
