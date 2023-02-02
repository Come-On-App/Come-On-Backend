package com.comeon.backend.meeting.infrastructure;

import com.comeon.backend.meeting.query.dao.MeetingMemberDao;
import com.comeon.backend.meeting.query.dto.MemberInfoResponse;
import com.comeon.backend.meeting.query.dto.MemberSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MeetingMemberDaoImpl implements MeetingMemberDao {

    private final MeetingMemberMapper meetingMemberMapper;

    @Override
    public MemberSimpleResponse findMemberSimple(Long meetingId, Long userId) {
        return meetingMemberMapper.selectMemberSimpleByMeetingIdAndUserId(meetingId, userId);
    }

    @Override
    public List<MemberInfoResponse> findMemberList(Long meetingId) {
        return meetingMemberMapper.selectMemberList(meetingId);
    }

    @Override
    public MemberInfoResponse findMember(Long meetingId, Long userId) {
        return meetingMemberMapper.selectMemberInfo(meetingId, userId);
    }
}
