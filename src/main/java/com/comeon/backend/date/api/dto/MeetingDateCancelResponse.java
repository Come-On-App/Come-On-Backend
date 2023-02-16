package com.comeon.backend.date.api.dto;

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
