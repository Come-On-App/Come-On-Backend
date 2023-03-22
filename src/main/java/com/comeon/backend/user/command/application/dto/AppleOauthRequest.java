package com.comeon.backend.user.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AppleOauthRequest {

    @NotBlank
    private String identityToken;

    @NotBlank
    private String user;

    private String email;
    private String name;
}
