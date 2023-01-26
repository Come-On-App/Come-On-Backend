package com.comeon.backend.jwt.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSimple {

    private Long userId;
    private String nickname;
    private String authorities;
}
