package com.comeon.backend.meeting.infrastructure;

import com.comeon.backend.meeting.query.dto.MemberSimpleResponse;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MeetingMemberMapper {

    MemberSimpleResponse selectMemberSimpleByMeetingIdAndUserId(Long meetingId, Long userId);
}
