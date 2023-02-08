package com.comeon.backend.meeting.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MeetingTimeModifyResponse {

    private boolean success;

    public MeetingTimeModifyResponse() {
        this.success = true;
    }
}
