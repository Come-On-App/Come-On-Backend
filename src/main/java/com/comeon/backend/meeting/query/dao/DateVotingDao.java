package com.comeon.backend.meeting.query.dao;

import com.comeon.backend.meeting.query.dto.VotingMemberSimple;
import com.comeon.backend.meeting.query.dto.DateVotingSimple;

import java.time.LocalDate;
import java.util.List;

public interface DateVotingDao {

    List<DateVotingSimple> findVotingSimpleListByMeetingIdWhetherMyVoting(Long meetingId, Long userId);

    List<VotingMemberSimple> findVotingMemberSimpleList(Long meetingId, LocalDate date);
}
