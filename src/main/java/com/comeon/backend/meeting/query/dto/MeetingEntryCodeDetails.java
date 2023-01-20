package com.comeon.backend.meeting.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MeetingEntryCodeDetails {

    private Long meetingId;
    private String entryCode;
    private LocalDateTime expiration;
}
