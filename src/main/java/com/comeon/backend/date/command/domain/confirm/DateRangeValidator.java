package com.comeon.backend.date.command.domain.confirm;

import java.time.LocalDate;

public interface DateRangeValidator {

    boolean verifyDateRangeInMeetingCalendar(Long meetingId, LocalDate startFrom, LocalDate endTo);
}
