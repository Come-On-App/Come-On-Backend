package com.comeon.backend.date.api.dto;

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
