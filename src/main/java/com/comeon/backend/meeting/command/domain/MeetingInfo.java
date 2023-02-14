package com.comeon.backend.meeting.command.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class MeetingInfo {

    private String meetingName;
    private String meetingImageUrl;
    private LocalDate calendarStartFrom;
    private LocalDate calendarEndTo;
}
