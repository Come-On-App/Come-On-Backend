package com.comeon.backend.meeting.command.application.v1;

import com.comeon.backend.meeting.command.domain.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LeaveMeetingFacade {

    private final MeetingRepository meetingRepository;

    public void leaveMeeting(Long meetingId, Long userId) {
        meetingRepository.findMeetingBy(meetingId)
                .ifPresentOrElse(meeting -> meeting.leave(userId), () -> {
                    throw new MeetingNotExistException(meetingId);
                });
    }
}
