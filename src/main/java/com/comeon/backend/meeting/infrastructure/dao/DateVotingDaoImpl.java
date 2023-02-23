package com.comeon.backend.meeting.infrastructure.dao;

import com.comeon.backend.meeting.infrastructure.mapper.DateVotingMapper;
import com.comeon.backend.meeting.query.dao.DateVotingDao;
import com.comeon.backend.meeting.query.dto.VotingMemberSimple;
import com.comeon.backend.meeting.query.dto.DateVotingSimple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DateVotingDaoImpl implements DateVotingDao {

    private final DateVotingMapper dateVotingMapper;

    @Override
    public List<DateVotingSimple> findVotingSimpleListByMeetingIdWhetherMyVoting(Long meetingId, Long userId) {
        return dateVotingMapper.selectVotingSimples(meetingId, userId);
    }

    @Override
    public List<VotingMemberSimple> findVotingMemberSimpleList(Long meetingId, LocalDate date) {
        return dateVotingMapper.selectVotingMemberSimples(meetingId, date);
    }
}
