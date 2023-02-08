package com.comeon.backend.user.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserSimple {

    private Long userId;
    private String nickname;
    private String role;
}
