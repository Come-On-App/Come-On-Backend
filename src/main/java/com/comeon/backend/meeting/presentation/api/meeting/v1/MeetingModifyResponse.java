package com.comeon.backend.meeting.presentation.api.meeting.v1;

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
