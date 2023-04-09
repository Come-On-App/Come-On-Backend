package com.comeon.backend.user.infrastructure.command;

import com.comeon.backend.meeting.command.application.v1.LeaveMeetingFacade;
import com.comeon.backend.meeting.query.dao.MeetingMemberDao;
import com.comeon.backend.user.command.domain.LeaveMeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveMeetingServiceImpl implements LeaveMeetingService {

    private final MeetingMemberDao meetingMemberDao;
    private final LeaveMeetingFacade leaveMeetingFacade;

    @Override
    public void leaveMeeting(Long userId) {
        List<Long> meetingIds = meetingMemberDao.findMeetingIds(userId);
        meetingIds.forEach(meetingId -> {
            leaveMeetingFacade.leaveMeeting(meetingId, userId);
        });
    }
}
