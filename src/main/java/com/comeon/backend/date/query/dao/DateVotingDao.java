package com.comeon.backend.date.query.dao;

import com.comeon.backend.date.query.dto.VotingMemberSimple;
import com.comeon.backend.date.query.dto.DateVotingSimple;

import java.time.LocalDate;
import java.util.List;

public interface DateVotingDao {

    List<DateVotingSimple> findVotingSimpleListByMeetingIdWhetherMyVoting(Long meetingId, Long userId);

    List<VotingMemberSimple> findVotingMemberSimpleList(Long meetingId, LocalDate date);
}
