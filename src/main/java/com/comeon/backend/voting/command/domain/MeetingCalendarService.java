package com.comeon.backend.voting.command.domain;

import java.time.LocalDate;

public interface MeetingCalendarService {

    boolean verifyDateInMeetingCalendar(Long meetingId, LocalDate date);
}
