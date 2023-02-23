package com.comeon.backend.meeting.command.application.dto;

import com.comeon.backend.meeting.command.domain.MeetingInfo;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
public class MeetingModifyRequest {

    private String meetingName;
    private String meetingImageUrl;

    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$")
    private String calendarStartFrom;

    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$")
    private String calendarEndTo;

    public String getMeetingName() {
        return meetingName;
    }

    public String getMeetingImageUrl() {
        return meetingImageUrl;
    }

    public LocalDate getCalendarStartFrom() {
        if (this.calendarStartFrom == null) {
            return null;
        }
        return LocalDate.parse(this.calendarStartFrom, DateTimeFormatter.ISO_DATE);
    }

    public LocalDate getCalendarEndTo() {
        if (this.calendarEndTo == null) {
            return null;
        }
        return LocalDate.parse(this.calendarEndTo, DateTimeFormatter.ISO_DATE);
    }

    public MeetingModifyRequest(String meetingName, String meetingImageUrl, LocalDate calendarStartFrom, LocalDate calendarEndTo) {
        this.meetingName = meetingName;
        this.meetingImageUrl = meetingImageUrl;
        if (calendarStartFrom != null) {
            this.calendarStartFrom = calendarStartFrom.format(DateTimeFormatter.ISO_DATE);
        }
        if (calendarEndTo != null) {
            this.calendarEndTo = calendarEndTo.format(DateTimeFormatter.ISO_DATE);
        }
    }

    public MeetingInfo toMeetingInfo() {
        return MeetingInfo.builder()
                .meetingName(this.meetingName)
                .meetingImageUrl(this.meetingImageUrl)
                .calendarStartFrom(this.getCalendarStartFrom())
                .calendarEndTo(this.getCalendarEndTo())
                .build();
    }
}
