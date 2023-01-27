package com.comeon.backend.meeting.query.dao;

import com.comeon.backend.meeting.query.dao.dto.MemberSimpleResponse;

public interface MeetingMemberDao {

    MemberSimpleResponse findMemberSimple(Long meetingId, Long userId);
}
