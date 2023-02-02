package com.comeon.backend.meeting.query.dao;

import com.comeon.backend.meeting.query.dto.MemberInfoResponse;
import com.comeon.backend.meeting.query.dto.MemberSimpleResponse;

import java.util.List;

public interface MeetingMemberDao {

    MemberSimpleResponse findMemberSimple(Long meetingId, Long userId);

    List<MemberInfoResponse> findMemberList(Long meetingId);

    MemberInfoResponse findMember(Long meetingId, Long userId);
}
