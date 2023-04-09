package com.comeon.backend.meeting.infrastructure.dao;

import com.comeon.backend.meeting.infrastructure.mapper.MeetingMemberMapper;
import com.comeon.backend.meeting.query.dao.MeetingMemberDao;
import com.comeon.backend.meeting.query.dto.MemberDetails;
import com.comeon.backend.meeting.query.dto.MemberSimple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MeetingMemberDaoImpl implements MeetingMemberDao {

    private final MeetingMemberMapper meetingMemberMapper;

    @Override
    public MemberSimple findMemberSimple(Long meetingId, Long userId) {
        return meetingMemberMapper.selectMemberSimpleByMeetingIdAndUserId(meetingId, userId);
    }

    @Override
    public List<MemberDetails> findMemberDetailsList(Long meetingId) {
        return meetingMemberMapper.selectMemberDetailsList(meetingId);
    }

    @Override
    public MemberDetails findMemberDetails(Long meetingId, Long userId) {
        return meetingMemberMapper.selectMemberDetails(meetingId, userId);
    }

    @Override
    public List<Long> findMeetingIds(Long userId) {
        return meetingMemberMapper.selectMeetingIds(userId);
    }
}
