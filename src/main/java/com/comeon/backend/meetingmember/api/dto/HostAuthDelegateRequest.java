package com.comeon.backend.meetingmember.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HostAuthDelegateRequest {

    @NotNull
    private Long targetUserId;
}
