package com.comeon.backend.meeting.presentation.api.meetingtime;

import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
public class MeetingTimeModifyRequest {

    @NotBlank
    @Pattern(regexp = "^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$")
    private String meetingStartTime;

    public MeetingTimeModifyRequest(LocalTime meetingStartTime) {
        this.meetingStartTime = meetingStartTime.format(DateTimeFormatter.ISO_LOCAL_TIME);
    }

    public LocalTime getMeetingStartTime() {
        return LocalTime.parse(meetingStartTime, DateTimeFormatter.ISO_LOCAL_TIME);
    }
}
