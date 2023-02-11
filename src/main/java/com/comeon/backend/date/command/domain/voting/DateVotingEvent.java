package com.comeon.backend.date.command.domain.voting;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DateVotingEvent {

    private Long targetMeetingId;
    private LocalDate targetDate;

    public static DateVotingEvent create(Long meetingId, LocalDate date) {
        return new DateVotingEvent(meetingId, date);
    }
}
