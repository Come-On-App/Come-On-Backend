package com.comeon.backend.meeting.presentation.api.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HostRoleDelegateRequest {

    @NotNull
    private Long targetUserId;
}
