package com.comeon.backend.meeting.command.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MeetingCalendarModifyEvent {

    private Long meetingId;
    private LocalDate calendarStartFrom;
    private LocalDate calendarEndTo;

    public static MeetingCalendarModifyEvent create(Long meetingId, LocalDate calendarStartFrom, LocalDate calendarEndTo) {
        return new MeetingCalendarModifyEvent(meetingId, calendarStartFrom, calendarEndTo);
    }
}
