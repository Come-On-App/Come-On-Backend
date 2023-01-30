package com.comeon.backend.date.query.application;

import com.comeon.backend.date.query.dto.VotingMemberSimple;
import com.comeon.backend.date.query.dao.DateVotingDao;
import com.comeon.backend.date.query.dto.VotingDetailsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DateVotingQueryService {

    private final DateVotingDao dateVotingDao;

    public VotingDetailsResponse votingDetails(Long meetingId, LocalDate date, Long userId) {
        List<VotingMemberSimple> memberSimpleList = dateVotingDao.findVotingMemberSimpleList(meetingId, date);
        boolean myVoting = memberSimpleList.stream()
                .anyMatch(memberSimple -> memberSimple.getUserId().equals(userId));
        return new VotingDetailsResponse(date, myVoting, memberSimpleList);
    }
}