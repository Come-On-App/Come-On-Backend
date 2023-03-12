package com.comeon.backend.user.api.v1.dto;

import com.comeon.backend.common.jwt.JwtToken;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserTokenResponse {

    private AtkResponse accessToken;
    private RtkResponse refreshToken;

    public static UserTokenResponse create(JwtToken atk, JwtToken rtk) {
        AtkResponse atkResponse = null;
        if (atk != null) {
            Long userId = atk.getPayload().getUserId();
            String atkValue = atk.getToken();
            long atkExpiry = atk.getPayload().getExpiration().toEpochMilli();
            atkResponse = new AtkResponse(atkValue, atkExpiry, userId);
        }

        RtkResponse rtkResponse = null;
        if (rtk != null) {
            String rtkValue = rtk.getToken();
            long rtkExpiry = rtk.getPayload().getExpiration().toEpochMilli();
            rtkResponse = new RtkResponse(rtkValue, rtkExpiry);
        }

        return new UserTokenResponse(atkResponse, rtkResponse);
    }

    @Getter
    @AllArgsConstructor
    public static class AtkResponse {
        private String token;
        private Long expiry;
        private Long userId;
    }

    @Getter
    @AllArgsConstructor
    public static class RtkResponse {
        private String token;
        private Long expiry;
    }
}
