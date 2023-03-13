package com.comeon.backend.user.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {

    private Long userId;
    private String nickname;
    private String profileImageUrl;
    private String role;
    private String email;
    private String name;
}
