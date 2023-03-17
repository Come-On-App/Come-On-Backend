package com.comeon.backend.admin.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLockRemoveResponse {

    private boolean success;

    public UserLockRemoveResponse() {
        this.success = true;
    }
}
