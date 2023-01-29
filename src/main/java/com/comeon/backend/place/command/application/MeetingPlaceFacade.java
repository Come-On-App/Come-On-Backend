package com.comeon.backend.place.command.application;

import com.comeon.backend.place.command.domain.MeetingPlace;
import com.comeon.backend.place.command.domain.MeetingPlaceRepository;
import com.comeon.backend.place.command.domain.PlaceNotExistException;
import com.comeon.backend.place.command.domain.PlaceOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MeetingPlaceFacade {

    private final MeetingPlaceRepository meetingPlaceRepository;
    private final PlaceOrderService placeOrderService;

    public Long addPlace(Long userId, Long meetingId, PlaceCommandDto.AddRequest request) {
        MeetingPlace place = placeOrderService.addPlaceAtLast(userId, meetingId, request.toPlaceInfo());
        return place.getId();
    }

    public void modifyPlace(Long userId, Long meetingId, Long placeId, PlaceCommandDto.ModifyRequest request) {
        MeetingPlace place = meetingPlaceRepository.findByMeetingIdAndPlaceId(meetingId, placeId)
                .orElseThrow(() -> new PlaceNotExistException(placeId));
        place.update(userId, request.toPlaceInfo());
    }

    public void removePlace(Long meetingId, Long placeId) {
        placeOrderService.removePlaceAndArrangeOrder(meetingId, placeId);
    }
}
