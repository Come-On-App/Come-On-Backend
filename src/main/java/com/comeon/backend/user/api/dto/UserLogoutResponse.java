package com.comeon.backend.user.api.dto;

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
