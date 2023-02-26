package com.comeon.backend.meeting.presentation.api.meetingplace.v2;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MeetingPlaceUnlockResponse {

    private boolean success;

    public MeetingPlaceUnlockResponse() {
        this.success = true;
    }
}
