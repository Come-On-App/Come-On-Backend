package com.comeon.backend.date.infrastructure.dao;

import com.comeon.backend.date.query.dto.VotingMemberSimple;
import com.comeon.backend.date.query.dto.DateVotingSimple;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DateVotingMapper {

    List<DateVotingSimple> selectVotingSimples(Long meetingId, Long userId);
    List<VotingMemberSimple> selectVotingMemberSimples(Long meetingId, LocalDate date);
}
