package com.comeon.backend.common.config.interceptor;

import com.comeon.backend.meeting.query.application.MeetingQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberRoleServiceImpl implements MemberRoleService {

    private final MeetingQueryService meetingQueryService;

    @Override
    public MemberRole getMemberRoleBy(Long meetingId, Long userId) {
        return meetingQueryService.findMemberSimple(meetingId, userId).getMemberRole();
    }
}
