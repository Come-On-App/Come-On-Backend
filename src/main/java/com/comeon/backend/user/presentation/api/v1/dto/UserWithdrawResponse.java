package com.comeon.backend.user.presentation.api.v1.dto;

import lombok.Getter;

@Getter
public class UserWithdrawResponse {

    private boolean success;

    public UserWithdrawResponse() {
        this.success = true;
    }
}
