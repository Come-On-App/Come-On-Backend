package com.comeon.backend.meetingmember.command.application.dto;

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
