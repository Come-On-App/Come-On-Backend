package com.comeon.backend.date.command.domain.voting;

import java.time.LocalDate;

public interface MeetingCalendarService {

    boolean verifyDateInMeetingCalendar(Long meetingId, LocalDate date);
}
