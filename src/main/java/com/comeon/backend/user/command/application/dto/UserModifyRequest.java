package com.comeon.backend.user.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserModifyRequest {

    @NotBlank
    private String nickname;

    private String profileImageUrl;
}
