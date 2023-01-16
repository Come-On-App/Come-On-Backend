package com.comeon.backend.user.presentation.api.response;

import lombok.Getter;

@Getter
public class UserModifyResponse {

    private Boolean success;

    public UserModifyResponse() {
        this.success = true;
    }
}
