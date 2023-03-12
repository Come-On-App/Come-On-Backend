package com.comeon.backend.user.query;

import com.comeon.backend.common.response.ProfileImageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class UserDetailsResponse {

    private Long userId;
    private String nickname;
    private ProfileImageType profileImageType;
    private String profileImageUrl;
    private String role;
    private String email;
    private String name;
}
