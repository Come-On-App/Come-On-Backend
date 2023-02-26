package com.comeon.backend.meeting.command.application.v1;

import com.comeon.backend.meeting.command.application.v1.dto.PlaceAddRequest;
import com.comeon.backend.meeting.command.domain.Meeting;
import com.comeon.backend.meeting.command.domain.MeetingPlace;
import com.comeon.backend.meeting.command.domain.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AddMeetingPlaceFacade {

    private final MeetingRepository meetingRepository;

    public Long addMeetingPlace(Long meetingId, PlaceAddRequest request) {
        Meeting meeting = meetingRepository.findMeetingBy(meetingId)
                .orElseThrow(() -> new MeetingNotExistException(meetingId));
        MeetingPlace meetingPlace = meeting.addPlace(request.toPlaceInfo());
        meetingRepository.flush();

        return meetingPlace.getId();
    }
}
