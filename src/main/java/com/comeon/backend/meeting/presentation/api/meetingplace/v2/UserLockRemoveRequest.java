package com.comeon.backend.meeting.presentation.api.meetingplace.v2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserLockRemoveRequest {

    @NotNull
    private Long userId;
}
