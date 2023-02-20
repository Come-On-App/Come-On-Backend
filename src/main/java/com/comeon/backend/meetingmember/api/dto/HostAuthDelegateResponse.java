package com.comeon.backend.meetingmember.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class HostAuthDelegateResponse {

    private boolean success;

    public HostAuthDelegateResponse() {
        this.success = true;
    }
}
