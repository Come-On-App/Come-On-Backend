package com.comeon.backend.common.config.interceptor;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MeetingMemberMapper {

    MemberSimpleResponse selectMemberSimpleByMeetingIdAndUserId(Long meetingId, Long userId);
}
