package com.comeon.backend.meeting.presentation.api.member;

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
