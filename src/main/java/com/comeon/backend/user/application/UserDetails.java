package com.comeon.backend.user.application;

import com.comeon.backend.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDetails {

    private Long userId;
    private String nickname;
    private String profileImageUrl;
    private String role;
    private String email;
    private String name;

    public UserDetails(User user) {
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.profileImageUrl = user.getProfileImageUrl();
        this.role = user.getRole().getValue();
        this.email = user.getEmail();
        this.name = user.getName();
    }
}
