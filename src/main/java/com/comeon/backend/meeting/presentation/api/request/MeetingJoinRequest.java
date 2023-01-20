package com.comeon.backend.meeting.presentation.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MeetingJoinRequest {

    @NotBlank
    private String entryCode;
}
