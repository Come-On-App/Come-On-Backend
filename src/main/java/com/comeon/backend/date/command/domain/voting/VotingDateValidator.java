package com.comeon.backend.date.command.domain.voting;

import java.time.LocalDate;

public interface VotingDateValidator {

    boolean verifyDateInMeetingCalendar(Long meetingId, LocalDate date);
}
