package com.comeon.backend.user.api.v1.dto;

import lombok.Getter;

@Getter
public class UserModifyResponse {

    private Boolean success;

    public UserModifyResponse() {
        this.success = true;
    }
}
