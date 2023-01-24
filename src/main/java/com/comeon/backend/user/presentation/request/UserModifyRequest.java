package com.comeon.backend.user.presentation.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserModifyRequest {

    private String nickname;
    private String profileImageUrl;
}