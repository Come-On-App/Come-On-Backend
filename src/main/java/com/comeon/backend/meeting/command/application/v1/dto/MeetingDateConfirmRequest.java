package com.comeon.backend.meeting.command.application.v1.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
public class MeetingDateConfirmRequest {

    @NotBlank
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$")
    private String meetingDateStartFrom;

    @NotBlank
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$")
    private String meetingDateEndTo;

    public LocalDate getMeetingDateStartFrom() {
        return LocalDate.parse(meetingDateStartFrom, DateTimeFormatter.ISO_DATE);
    }

    public LocalDate getMeetingDateEndTo() {
        return LocalDate.parse(meetingDateEndTo, DateTimeFormatter.ISO_DATE);
    }
}
