package com.comeon.backend.meeting.infrastructure;

import com.comeon.backend.meeting.query.dao.MeetingMemberDao;
import com.comeon.backend.meeting.query.dto.MemberSimpleResponse;
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
