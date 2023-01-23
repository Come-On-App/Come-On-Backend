package com.comeon.backend.user.command.infra.oauth.feign.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) // snake_case json to camelCase dto
public class KakaoOauthTokenResponse {

    private String tokenType;
    private String accessToken;
    private Integer expiresIn;
    private String idToken;
    private String refreshToken;
    private Integer refreshTokenExpiresIn;
    private String scope;
}
