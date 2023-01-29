package com.comeon.backend.meeting.query.application;

import com.comeon.backend.meeting.query.dao.MeetingMemberDao;
import com.comeon.backend.meeting.query.dto.MemberSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MeetingQueryService {

    private final MeetingMemberDao meetingMemberDao;

    public MemberSimpleResponse findMemberSimple(Long meetingId, Long userId) {
        MemberSimpleResponse memberSimple = meetingMemberDao.findMemberSimple(meetingId, userId);
        if (memberSimple == null) {
            throw new NotMemberException("모임에 가입되지 않은 유저입니다. meetingId: " + meetingId + ", userId: " + userId);
        }
        return memberSimple;
    }
}
