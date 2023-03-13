package com.comeon.backend.meeting.presentation.api.member.v1;

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
