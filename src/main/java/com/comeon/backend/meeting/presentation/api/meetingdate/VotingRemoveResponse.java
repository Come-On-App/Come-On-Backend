package com.comeon.backend.meeting.presentation.api.meetingdate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VotingRemoveResponse {

    private Boolean success;

    public VotingRemoveResponse() {
        this.success = true;
    }
}
