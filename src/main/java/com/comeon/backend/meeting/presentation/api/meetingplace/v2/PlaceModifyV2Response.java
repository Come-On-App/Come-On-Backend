package com.comeon.backend.meeting.presentation.api.meetingplace.v2;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlaceModifyV2Response {

    private boolean success;

    public PlaceModifyV2Response() {
        this.success = true;
    }
}
