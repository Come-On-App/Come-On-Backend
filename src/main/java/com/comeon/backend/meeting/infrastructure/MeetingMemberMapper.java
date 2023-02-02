package com.comeon.backend.meeting.infrastructure;

import com.comeon.backend.meeting.query.dto.MemberInfoResponse;
import com.comeon.backend.meeting.query.dto.MemberSimpleResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MeetingMemberMapper {

    MemberSimpleResponse selectMemberSimpleByMeetingIdAndUserId(Long meetingId, Long userId);
    List<MemberInfoResponse> selectMemberList(Long meetingId);
    MemberInfoResponse selectMemberInfo(Long meetingId, Long userId);
}
