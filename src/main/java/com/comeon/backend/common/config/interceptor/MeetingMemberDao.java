package com.comeon.backend.common.config.interceptor;

public interface MeetingMemberDao {

    MemberSimpleResponse findMemberSimple(Long meetingId, Long userId);
}
