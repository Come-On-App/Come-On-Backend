package com.comeon.backend.user.api.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLogoutResponse {

    private boolean success;

    public UserLogoutResponse() {
        this.success = true;
    }
}
