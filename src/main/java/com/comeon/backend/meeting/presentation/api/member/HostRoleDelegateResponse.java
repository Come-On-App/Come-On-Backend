package com.comeon.backend.meeting.presentation.api.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HostRoleDelegateResponse {

    private boolean success;

    public HostRoleDelegateResponse() {
        this.success = true;
    }
}
