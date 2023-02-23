package com.comeon.backend.meeting.presentation.api.meetingdate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MeetingDateConfirmResponse {

    private Boolean success;

    public MeetingDateConfirmResponse() {
        this.success = true;
    }
}
