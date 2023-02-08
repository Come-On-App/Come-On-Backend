package com.comeon.backend.common.producer;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class MeetingResourceUpdatedMessage {

    private Long meetingId;
    private TargetResourceOfMeeting targetResource;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

    public MeetingResourceUpdatedMessage(Long meetingId, TargetResourceOfMeeting targetResource) {
        this.meetingId = meetingId;
        this.targetResource = targetResource;
        this.updatedAt = LocalDateTime.now();
    }
}
