package com.comeon.backend.meeting.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class EntryCodeDetails {

    private Long meetingId;
    private String entryCode;
    private LocalDateTime expiration;
}
