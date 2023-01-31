package com.comeon.backend.meeting.query.dao;

import com.comeon.backend.meeting.query.dto.MemberListResponse;
import com.comeon.backend.meeting.query.dto.MemberSimpleResponse;

import java.util.List;

public interface MeetingMemberDao {

    MemberSimpleResponse findMemberSimple(Long meetingId, Long userId);

    List<MemberListResponse> findMemberList(Long meetingId);
}
