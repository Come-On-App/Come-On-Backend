package com.comeon.backend.meeting.presentation;

import com.comeon.backend.common.security.JwtPrincipal;
import com.comeon.backend.meeting.command.application.MeetingPlaceFacade;
import com.comeon.backend.meeting.presentation.request.PlaceAddRequest;
import com.comeon.backend.meeting.presentation.response.PlaceAddResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/meetings/{meetingId}/places")
public class MeetingPlaceApiController {

    private final MeetingPlaceFacade meetingPlaceFacade;

    @PostMapping
    public PlaceAddResponse placeAdd(@AuthenticationPrincipal JwtPrincipal jwtPrincipal,
                                     @PathVariable Long meetingId,
                                     @RequestBody PlaceAddRequest request) {
        Long meetingPlaceId = meetingPlaceFacade.addPlace(
                jwtPrincipal.getUserId(), meetingId,
                request.getName(), request.getMemo(),
                request.getLat(), request.getLng(),
                request.getAddress(), request.getCategory(),
                request.getGooglePlaceId()
        );
        return new PlaceAddResponse(meetingPlaceId);
    }
}
