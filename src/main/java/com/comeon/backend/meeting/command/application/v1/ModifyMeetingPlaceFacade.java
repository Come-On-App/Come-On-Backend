package com.comeon.backend.meeting.command.application.v1;

import com.comeon.backend.meeting.command.application.v1.dto.PlaceModifyRequest;
import com.comeon.backend.meeting.command.domain.Meeting;
import com.comeon.backend.meeting.command.domain.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ModifyMeetingPlaceFacade {

    private final MeetingRepository meetingRepository;

    public void modifyMeetingPlace(Long meetingId, Long meetingPlaceId, PlaceModifyRequest request) {
        Meeting meeting = meetingRepository.findMeetingBy(meetingId)
                .orElseThrow(() -> new MeetingNotExistException(meetingId));
        meeting.modifyPlace(meetingPlaceId, request.toPlaceInfo());
    }
}
