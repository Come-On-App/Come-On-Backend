package com.comeon.backend.meeting.presentation.api.member.v1;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MeetingLeaveResponse {

    private boolean success;

    public MeetingLeaveResponse() {
        this.success = true;
    }
}
