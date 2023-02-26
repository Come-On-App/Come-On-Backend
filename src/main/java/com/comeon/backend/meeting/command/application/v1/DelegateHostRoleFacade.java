package com.comeon.backend.meeting.command.application.v1;

import com.comeon.backend.meeting.command.domain.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DelegateHostRoleFacade {

    private final MeetingRepository meetingRepository;

    public void delegateHostRole(Long meetingId, Long targetUserId) {
        meetingRepository.findMeetingBy(meetingId)
                .ifPresentOrElse(meeting -> meeting.changeHost(targetUserId), () -> {
                    throw new MeetingNotExistException(meetingId);
                });
    }
}
