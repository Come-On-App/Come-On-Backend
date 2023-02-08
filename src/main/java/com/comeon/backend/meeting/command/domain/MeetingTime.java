package com.comeon.backend.meeting.command.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalTime;

@Embeddable
public class MeetingTime {

    @Column(name = "meeting_start_time")
    private LocalTime startTime;

    public MeetingTime() {
        this.startTime = LocalTime.of(8,0,0);
    }

    public MeetingTime(LocalTime startTime) {
        this.startTime = startTime;
    }
}
