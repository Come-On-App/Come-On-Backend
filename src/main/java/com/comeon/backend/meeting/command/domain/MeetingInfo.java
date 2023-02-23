package com.comeon.backend.meeting.command.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class MeetingInfo {

    private String meetingName;
    private String meetingImageUrl;
    private LocalDate calendarStartFrom;
    private LocalDate calendarEndTo;
}
