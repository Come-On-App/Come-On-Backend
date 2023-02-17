package com.comeon.backend.date.infrastructure.dao;

import com.comeon.backend.date.query.dao.FixedDateDao;
import com.comeon.backend.date.query.dto.MeetingFixedDateSimple;
import com.comeon.backend.date.query.dto.MeetingFixedDateSummary;
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
