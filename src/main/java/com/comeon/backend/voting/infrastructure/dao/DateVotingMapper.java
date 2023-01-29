package com.comeon.backend.voting.infrastructure.dao;

import com.comeon.backend.voting.query.dto.VotingMemberSimple;
import com.comeon.backend.voting.query.dto.VotingSimpleResponse;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DateVotingMapper {

    List<VotingSimpleResponse> selectVotingSimples(Long meetingId, Long userId);
    List<VotingMemberSimple> selectVotingMemberSimples(Long meetingId, LocalDate date);
}
