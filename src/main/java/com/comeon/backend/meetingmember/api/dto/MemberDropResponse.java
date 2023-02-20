package com.comeon.backend.meetingmember.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberDropResponse {

    private boolean success;

    public MemberDropResponse() {
        this.success = true;
    }
}
