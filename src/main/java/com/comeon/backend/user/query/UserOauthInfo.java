package com.comeon.backend.user.query;

import com.comeon.backend.user.command.domain.OauthProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserOauthInfo {

    private Long userId;
    private OauthProvider provider;
    private String email;
    private String name;
}
