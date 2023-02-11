package com.comeon.backend.place.command.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceOrderService {

    private final MeetingPlaceRepository meetingPlaceRepository;

    public MeetingPlace addPlaceAtLast(Long userId, Long meetingId, PlaceInfo placeInfo) {
        List<MeetingPlace> places = meetingPlaceRepository.findByMeetingId(meetingId);
        int placeCount = places.size();
        MeetingPlace place = MeetingPlace.builder()
                .userId(userId)
                .meetingId(meetingId)
                .placeInfo(placeInfo)
                .placeOrder(placeCount + 1)
                .build();
        return meetingPlaceRepository.save(place);
    }

    public void removePlaceAndArrangeOrder(Long meetingId, Long placeId) {
        List<MeetingPlace> places = meetingPlaceRepository.findByMeetingId(meetingId);
        MeetingPlace placeToRemove = places.stream()
                .filter(place -> place.getId().equals(placeId))
                .findFirst()
                .orElseThrow(() -> new PlaceNotExistException(placeId));
        places.remove(placeToRemove);
        meetingPlaceRepository.delete(placeToRemove);

        int removedOrder = placeToRemove.getOrder();
        places.stream()
                .filter(place -> place.getOrder() > removedOrder)
                .forEach(MeetingPlace::decreaseOrder);
    }
}
