package com.comeon.backend.meeting.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MeetingModifyResponse {

    private boolean success;

    public MeetingModifyResponse() {
        this.success = true;
    }
}
