package com.comeon.backend.meeting.presentation.api.member.v1;

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
