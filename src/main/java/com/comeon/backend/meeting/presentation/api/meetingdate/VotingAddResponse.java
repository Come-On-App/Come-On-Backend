package com.comeon.backend.meeting.presentation.api.meetingdate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VotingAddResponse {

    private Boolean success;

    public VotingAddResponse() {
        this.success = true;
    }
}
