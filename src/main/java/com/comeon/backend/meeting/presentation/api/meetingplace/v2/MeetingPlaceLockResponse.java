package com.comeon.backend.meeting.presentation.api.meetingplace.v2;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MeetingPlaceLockResponse {

    private boolean success;

    public MeetingPlaceLockResponse() {
        this.success = true;
    }
}
