package com.comeon.backend.date.infrastructure.dao;

import com.comeon.backend.date.query.dao.DateVotingDao;
import com.comeon.backend.date.query.dto.VotingMemberSimple;
import com.comeon.backend.date.query.dto.VotingSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DateVotingDaoImpl implements DateVotingDao {

    private final DateVotingMapper dateVotingMapper;

    @Override
    public List<VotingSimpleResponse> findVotingSimpleListByMeetingIdWhetherMyVoting(Long meetingId, Long userId) {
        return dateVotingMapper.selectVotingSimples(meetingId, userId);
    }

    @Override
    public List<VotingMemberSimple> findVotingMemberSimpleList(Long meetingId, LocalDate date) {
        return dateVotingMapper.selectVotingMemberSimples(meetingId, date);
    }
}
