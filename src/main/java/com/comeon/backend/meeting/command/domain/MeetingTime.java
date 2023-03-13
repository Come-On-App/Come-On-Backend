package com.comeon.backend.meeting.command.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalTime;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingTime {

    @Column(name = "meeting_start_time")
    private LocalTime startTime;

    public static MeetingTime init() {
        return new MeetingTime(LocalTime.of(8,0,0));
    }

    public static MeetingTime create(LocalTime startTime) {
        return new MeetingTime(startTime);
    }
}
