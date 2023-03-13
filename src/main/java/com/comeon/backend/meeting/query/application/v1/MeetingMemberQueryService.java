package com.comeon.backend.meeting.query.application.v1;

import com.comeon.backend.meeting.query.application.NotMemberException;
import com.comeon.backend.meeting.query.dao.MeetingMemberDao;
import com.comeon.backend.meeting.query.dto.MemberSimple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MeetingMemberQueryService {

    private final MeetingMemberDao meetingMemberDao;

    public MemberSimple findMemberSimple(Long meetingId, Long userId) {
        MemberSimple memberSimple = meetingMemberDao.findMemberSimple(meetingId, userId);
        if (memberSimple == null) {
            throw new NotMemberException("모임에 가입되지 않은 유저입니다. meetingId: " + meetingId + ", userId: " + userId);
        }
        return memberSimple;
    }
}
