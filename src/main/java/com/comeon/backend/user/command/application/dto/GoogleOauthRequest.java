package com.comeon.backend.user.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GoogleOauthRequest {

    @NotBlank
    private String idToken;
}
