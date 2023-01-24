package com.comeon.backend.meeting.command.application;

import com.comeon.backend.meeting.command.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MeetingPlaceFacade {

    private final MeetingRepository meetingRepository;

    public Long addPlace(Long userId, Long meetingId,
                         String placeName, String placeMemo, Double lat, Double lng,
                         String address, String category, String googlePlaceId) {
        Meeting meeting = meetingRepository.findMeetingFetchPlacesBy(meetingId)
                .orElseThrow(() -> new MeetingNotExistException(meetingId));
        MeetingPlace place = meeting.createPlace(
                userId, placeName, placeMemo, lat, lng,
                address, category, googlePlaceId
        );
        meetingRepository.flush();

        return place.getId();
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
