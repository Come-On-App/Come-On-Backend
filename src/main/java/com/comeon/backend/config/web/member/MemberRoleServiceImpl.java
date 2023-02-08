package com.comeon.backend.config.web.member;

import com.comeon.backend.meetingmember.query.application.MeetingMemberQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberRoleServiceImpl implements MemberRoleService {

    private final MeetingMemberQueryService meetingMemberQueryService;

    @Override
    public MemberRole getMemberRoleBy(Long meetingId, Long userId) {
        return meetingMemberQueryService.findMemberSimple(meetingId, userId).getMemberRole();
    }
}
