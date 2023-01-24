package com.comeon.backend.jwt.presentation.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JwtReissueRequest {

    @NotBlank
    private String refreshToken;
    private Boolean reissueRefreshTokenAlways = false;
}
