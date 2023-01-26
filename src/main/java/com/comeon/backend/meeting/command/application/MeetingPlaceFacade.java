package com.comeon.backend.meeting.command.application;

import com.comeon.backend.meeting.command.application.dto.PlaceCommandDto;
import com.comeon.backend.meeting.command.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MeetingPlaceFacade {

    private final MeetingRepository meetingRepository;

    public Long addPlace(Long userId, Long meetingId, PlaceCommandDto.AddRequest request) {
        Meeting meeting = meetingRepository.findMeetingFetchPlacesBy(meetingId)
                .orElseThrow(() -> new MeetingNotExistException(meetingId));
        MeetingPlace place = meeting.createPlace(userId, request.toPlaceInfo());

        meetingRepository.flush();

        return place.getId();
    }

    public void modifyPlace(Long userId, Long meetingId, Long placeId, PlaceCommandDto.ModifyRequest request) {
        Meeting meeting = meetingRepository.findMeetingFetchPlacesBy(meetingId)
                .orElseThrow(() -> new MeetingNotExistException(meetingId));
        meeting.modifyPlaceByPlaceId(placeId, userId, request.toPlaceInfo());
    }

    public void removePlace(Long meetingId, Long placeId) {
        meetingRepository.findMeetingFetchPlacesBy(meetingId)
                .ifPresentOrElse(
                        meeting -> meeting.removePlaceByPlaceId(placeId),
                        () -> {
                            throw new MeetingNotExistException(meetingId);
                        }
                );
    }
}
