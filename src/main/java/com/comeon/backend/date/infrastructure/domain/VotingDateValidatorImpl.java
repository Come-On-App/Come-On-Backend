package com.comeon.backend.date.infrastructure.domain;

import com.comeon.backend.meeting.query.dao.MeetingDao;
import com.comeon.backend.meeting.query.dto.MeetingCalendarResponse;
import com.comeon.backend.date.command.domain.voting.VotingDateValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class VotingDateValidatorImpl implements VotingDateValidator {

    private final MeetingDao meetingDao;

    @Override
    public boolean verifyDateInMeetingCalendar(Long meetingId, LocalDate date) {
        MeetingCalendarResponse meetingCalendar = meetingDao.findMeetingCalendar(meetingId);
        return !meetingCalendar.getStartFrom().isAfter(date)
                && !meetingCalendar.getEndTo().isBefore(date);
    }
}
