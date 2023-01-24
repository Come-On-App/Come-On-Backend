package com.comeon.backend.meeting.command.application;

import com.comeon.backend.meeting.command.domain.Meeting;
import com.comeon.backend.meeting.command.domain.MeetingPlace;
import com.comeon.backend.meeting.command.domain.MeetingRepository;
import com.comeon.backend.meeting.command.domain.PlaceCategory;
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
        Meeting meeting = meetingRepository.findMeetingBy(meetingId).orElseThrow();
        MeetingPlace place = MeetingPlace.builder()
                .meeting(meeting)
                .name(placeName)
                .memo(placeMemo)
                .lat(lat)
                .lng(lng)
                .address(address)
                .order(meetingRepository.getPlaceCountBy(meetingId) + 1)
                .category(PlaceCategory.of(category))
                .googlePlaceId(googlePlaceId)
                .userId(userId)
                .build();

        return meetingRepository.savePlace(place).getId();
    }
}
