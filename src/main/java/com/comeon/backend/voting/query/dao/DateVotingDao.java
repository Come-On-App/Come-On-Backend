package com.comeon.backend.voting.query.dao;

import com.comeon.backend.voting.query.dto.VotingMemberSimple;
import com.comeon.backend.voting.query.dto.VotingSimpleResponse;

import java.time.LocalDate;
import java.util.List;

public interface DateVotingDao {

    List<VotingSimpleResponse> findVotingSimpleListByMeetingIdWhetherMyVoting(Long meetingId, Long userId);

    List<VotingMemberSimple> findVotingMemberSimpleList(Long meetingId, LocalDate date);
}
