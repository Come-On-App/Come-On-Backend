package com.comeon.backend.meeting.presentation.api.meetingplace.v2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class UserLockRemoveResponse {

    private boolean success;

    public UserLockRemoveResponse() {
        this.success = true;
    }
}
