package com.comeon.backend.date.infrastructure.domain;

import com.comeon.backend.meeting.query.dao.MeetingDao;
import com.comeon.backend.meeting.query.dto.MeetingCalendarResponse;
import com.comeon.backend.date.command.domain.voting.MeetingCalendarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class MeetingCalendarServiceImpl implements MeetingCalendarService {

    private final MeetingDao meetingDao;

    @Override
    public boolean verifyDateInMeetingCalendar(Long meetingId, LocalDate date) {
        MeetingCalendarResponse meetingCalendar = meetingDao.findMeetingCalendar(meetingId);
        return !meetingCalendar.getStartFrom().isAfter(date)
                && !meetingCalendar.getEndTo().isBefore(date);
    }
}
