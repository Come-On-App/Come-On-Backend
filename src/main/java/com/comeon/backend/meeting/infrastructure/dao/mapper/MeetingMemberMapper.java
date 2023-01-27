package com.comeon.backend.meeting.infrastructure.dao.mapper;

import com.comeon.backend.meeting.query.dao.dto.MemberSimpleResponse;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MeetingMemberMapper {

    MemberSimpleResponse selectMemberSimpleByMeetingIdAndUserId(Long meetingId, Long userId);
}
