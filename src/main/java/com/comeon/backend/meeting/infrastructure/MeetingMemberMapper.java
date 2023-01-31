package com.comeon.backend.meeting.infrastructure;

import com.comeon.backend.meeting.query.dto.MemberListResponse;
import com.comeon.backend.meeting.query.dto.MemberSimpleResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MeetingMemberMapper {

    MemberSimpleResponse selectMemberSimpleByMeetingIdAndUserId(Long meetingId, Long userId);
    List<MemberListResponse> selectMemberList(Long meetingId);
}
