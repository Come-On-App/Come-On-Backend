package com.comeon.backend.meeting.presentation.api.meetingplace;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlaceRemoveResponse {

    private boolean success;

    public PlaceRemoveResponse() {
        this.success = true;
    }
}
