package com.comeon.backend.user.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDetailsResponse {

    private Long userId;
    private String nickname;
    private String profileImageUrl;
    private String role;
    private String email;
    private String name;
}
