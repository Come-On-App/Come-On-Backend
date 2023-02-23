package com.comeon.backend.meeting.command.application.dto;

import com.comeon.backend.meeting.command.domain.EntryCode;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class EntryCodeRenewResponse {

    private Long meetingId;
    private String entryCode;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime expiredAt;

    public EntryCodeRenewResponse(Long meetingId, EntryCode entryCode) {
        this.meetingId = meetingId;
        this.entryCode = entryCode.getCode();
        this.expiredAt = entryCode.getUpdatedAt().plusDays(7);
    }
}
