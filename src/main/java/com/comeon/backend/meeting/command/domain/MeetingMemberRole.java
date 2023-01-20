package com.comeon.backend.meeting.command.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MeetingMemberRole {
    HOST("모임 방장 권한"),
    PARTICIPANT("모임 참가자 권한"),
    ;

    private final String description;
}
