package com.comeon.backend.date.command.application.confirm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
public class FixedDateAddRequest {

    @NotBlank
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$")
    private String meetingDateStartFrom;

    @NotBlank
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$")
    private String meetingDateEndTo;

    @NotBlank
    @Pattern(regexp = "^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$")
    private String meetingStartTime;

    public LocalDate getMeetingDateStartFrom() {
        return LocalDate.parse(meetingDateStartFrom, DateTimeFormatter.ISO_DATE);
    }

    public LocalDate getMeetingDateEndTo() {
        return LocalDate.parse(meetingDateEndTo, DateTimeFormatter.ISO_DATE);
    }

    public LocalTime getMeetingStartTime() {
        return LocalTime.parse(meetingStartTime, DateTimeFormatter.ISO_LOCAL_TIME);
    }
}