package com.comeon.backend.date.infrastructure.domain;

import com.comeon.backend.date.command.domain.confirm.DateRangeValidator;
import com.comeon.backend.meeting.query.dao.MeetingDao;
import com.comeon.backend.meeting.query.dto.MeetingCalendarResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DateRangeValidatorImpl implements DateRangeValidator {

    private final MeetingDao meetingDao;

    @Override
    public boolean verifyDateRangeInMeetingCalendar(Long meetingId, LocalDate startFrom, LocalDate endTo) {
        MeetingCalendarResponse meetingCalendar = meetingDao.findMeetingCalendar(meetingId);
        return !meetingCalendar.getStartFrom().isAfter(startFrom)
                && !meetingCalendar.getStartFrom().isAfter(endTo)
                && !meetingCalendar.getEndTo().isBefore(startFrom)
                && !meetingCalendar.getEndTo().isBefore(endTo);
    }
}
