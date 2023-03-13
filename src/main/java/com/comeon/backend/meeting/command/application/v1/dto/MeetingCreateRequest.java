package com.comeon.backend.meeting.command.application.v1.dto;

import com.comeon.backend.meeting.command.domain.MeetingInfo;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
public class MeetingCreateRequest {

    @NotBlank
    private String meetingName;

    @NotBlank
    private String meetingImageUrl;

    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$")
    @NotBlank
    private String calendarStartFrom;

    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$")
    @NotBlank
    private String calendarEndTo;

    public String getMeetingName() {
        return meetingName;
    }

    public String getMeetingImageUrl() {
        return meetingImageUrl;
    }

    public LocalDate getCalendarStartFrom() {
        return LocalDate.parse(calendarStartFrom, DateTimeFormatter.ISO_DATE);
    }

    public LocalDate getCalendarEndTo() {
        return LocalDate.parse(calendarEndTo, DateTimeFormatter.ISO_DATE);
    }

    public MeetingCreateRequest(String meetingName, String meetingImageUrl, LocalDate calendarStartFrom, LocalDate calendarEndTo) {
        this.meetingName = meetingName;
        this.meetingImageUrl = meetingImageUrl;
        this.calendarStartFrom = calendarStartFrom.format(DateTimeFormatter.ISO_DATE);
        this.calendarEndTo = calendarEndTo.format(DateTimeFormatter.ISO_DATE);
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
