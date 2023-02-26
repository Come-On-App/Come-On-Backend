package com.comeon.backend.meeting.command.application.v2;

import com.comeon.backend.meeting.command.application.v1.MeetingNotExistException;
import com.comeon.backend.meeting.command.application.v2.dto.PlaceModifyV2Request;
import com.comeon.backend.meeting.command.domain.Meeting;
import com.comeon.backend.meeting.command.domain.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ModifyMeetingPlaceFacadeV2 {

    private final MeetingRepository meetingRepository;

    public void modifyMeetingPlace(Long userId, Long meetingId, Long meetingPlaceId, PlaceModifyV2Request request) {
        Meeting meeting = meetingRepository.findMeetingBy(meetingId)
                .orElseThrow(() -> new MeetingNotExistException(meetingId));
        meeting.modifyPlaceWithUnlock(userId, meetingPlaceId, request.toPlaceInfo());
    }
}
