package com.comeon.backend.meeting.command.domain.event;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DateVotingListUpdateEvent {

    private Long meetingId;
    private LocalDate date;

    public static DateVotingListUpdateEvent create(Long meetingId, LocalDate date) {
        return new DateVotingListUpdateEvent(meetingId, date);
    }
}
