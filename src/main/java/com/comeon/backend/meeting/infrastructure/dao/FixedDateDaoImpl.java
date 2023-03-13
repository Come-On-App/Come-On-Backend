package com.comeon.backend.meeting.infrastructure.dao;

import com.comeon.backend.meeting.infrastructure.mapper.FixedDateMapper;
import com.comeon.backend.meeting.query.dao.FixedDateDao;
import com.comeon.backend.meeting.query.dto.MeetingFixedDateSimple;
import com.comeon.backend.meeting.query.dto.MeetingFixedDateSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FixedDateDaoImpl implements FixedDateDao {

    private final FixedDateMapper fixedDateMapper;

    @Override
    public MeetingFixedDateSimple findFixedDateSimple(Long meetingId) {
        return fixedDateMapper.selectFixedDateSimple(meetingId);
    }

    @Override
    public List<MeetingFixedDateSummary> findMeetingFixedDatesSummary(Long userId, LocalDate searchStartFrom, LocalDate searchEndTo) {
        return fixedDateMapper.selectMeetingFixedDateSummary(userId, searchStartFrom, searchEndTo);
    }
}
