package com.comeon.backend.user.command.application.dto;

import com.comeon.backend.user.command.domain.User;
import lombok.Getter;

@Getter
public class LoginUserDto {

    private Long userId;
    private String nickname;
    private String authorities;

    public LoginUserDto(User user) {
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.authorities = user.getRole().getValue();
    }
}
