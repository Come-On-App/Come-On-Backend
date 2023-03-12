package com.comeon.backend.user.api.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserTokenReissueRequest {

    @NotBlank
    private String refreshToken;
    private Boolean reissueRefreshTokenAlways = false;
}
