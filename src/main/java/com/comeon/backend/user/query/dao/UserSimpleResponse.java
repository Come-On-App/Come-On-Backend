package com.comeon.backend.user.query.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserSimpleResponse {

    private Long userId;
    private String nickname;
    private String role;
}
