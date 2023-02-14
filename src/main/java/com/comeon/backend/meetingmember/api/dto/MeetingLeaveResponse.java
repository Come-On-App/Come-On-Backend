package com.comeon.backend.meetingmember.api.dto;

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
