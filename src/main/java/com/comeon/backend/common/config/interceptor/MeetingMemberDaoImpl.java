package com.comeon.backend.common.config.interceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MeetingMemberDaoImpl implements MeetingMemberDao {

    private final MeetingMemberMapper meetingMemberMapper;

    @Override
    public MemberSimpleResponse findMemberSimple(Long meetingId, Long userId) {
        return meetingMemberMapper.selectMemberSimpleByMeetingIdAndUserId(meetingId, userId);
    }
}
