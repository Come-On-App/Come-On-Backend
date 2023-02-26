package com.comeon.backend.meeting.command.application.v2;

import com.comeon.backend.meeting.command.application.v1.MeetingNotExistException;
import com.comeon.backend.meeting.command.domain.Meeting;
import com.comeon.backend.meeting.command.domain.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LockMeetingPlaceFacade {

    private final MeetingRepository meetingRepository;

    public void lockMeetingPlace(Long userId, Long meetingId, Long meetingPlaceId) {
        Meeting meeting = meetingRepository.findMeetingBy(meetingId)
                .orElseThrow(() -> new MeetingNotExistException(meetingId));
        meeting.lockPlace(userId, meetingPlaceId);
    }
}
