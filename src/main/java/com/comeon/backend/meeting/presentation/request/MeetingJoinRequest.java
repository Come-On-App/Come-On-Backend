package com.comeon.backend.meeting.presentation.request;

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
