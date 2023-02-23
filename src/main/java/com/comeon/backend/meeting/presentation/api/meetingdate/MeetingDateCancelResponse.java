package com.comeon.backend.meeting.presentation.api.meetingdate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MeetingDateCancelResponse {

    private Boolean success;

    public MeetingDateCancelResponse() {
        this.success = true;
    }
}
