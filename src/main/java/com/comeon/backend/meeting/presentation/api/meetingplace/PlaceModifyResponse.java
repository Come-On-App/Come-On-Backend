package com.comeon.backend.meeting.presentation.api.meetingplace;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlaceModifyResponse {

    private boolean success;

    public PlaceModifyResponse() {
        this.success = true;
    }
}
