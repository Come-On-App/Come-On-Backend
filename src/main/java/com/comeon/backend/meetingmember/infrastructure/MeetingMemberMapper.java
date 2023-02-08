package com.comeon.backend.meetingmember.infrastructure;

import com.comeon.backend.meetingmember.query.dto.MemberDetails;
import com.comeon.backend.meetingmember.query.dto.MemberSimple;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MeetingMemberMapper {

    MemberSimple selectMemberSimpleByMeetingIdAndUserId(Long meetingId, Long userId);
    List<MemberDetails> selectMemberDetailsList(Long meetingId);
    MemberDetails selectMemberDetails(Long meetingId, Long userId);
}
