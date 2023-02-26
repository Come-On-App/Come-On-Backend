package com.comeon.backend.meeting.command.application.v1;

import com.comeon.backend.meeting.command.domain.Meeting;
import com.comeon.backend.meeting.command.domain.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RemoveMeetingPlaceFacade {

    private final MeetingRepository meetingRepository;

    public void removeMeetingPlace(Long meetingId, Long meetingPlaceId) {
        Meeting meeting = meetingRepository.findMeetingBy(meetingId)
                .orElseThrow(() -> new MeetingNotExistException(meetingId));
        meeting.removePlace(meetingPlaceId);
    }
}
