package com.comeon.backend.meeting.presentation;

import com.comeon.backend.common.api.ListResponse;
import com.comeon.backend.common.security.JwtPrincipal;
import com.comeon.backend.meeting.command.application.MeetingPlaceFacade;
import com.comeon.backend.meeting.presentation.request.PlaceAddRequest;
import com.comeon.backend.meeting.presentation.response.PlaceAddResponse;
import com.comeon.backend.meeting.presentation.response.PlaceListResponse;
import com.comeon.backend.meeting.query.dao.MeetingPlaceDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/meetings/{meetingId}/places")
public class MeetingPlaceApiController {

    private final MeetingPlaceFacade meetingPlaceFacade;
    private final MeetingPlaceDao meetingPlaceDao;

    @GetMapping
    public ListResponse<?> placeList(@AuthenticationPrincipal JwtPrincipal jwtPrincipal,
                                     @PathVariable Long meetingId) {
        return ListResponse.toListResponse(
                meetingPlaceDao.findPlaceList(meetingId).stream()
                        .map(
                                result -> new PlaceListResponse(
                                        result.getMeetingPlaceId(),
                                        result.getPlaceName(),
                                        result.getMemo(),
                                        result.getLat(),
                                        result.getLng(),
                                        result.getAddress(),
                                        result.getOrder(),
                                        result.getCategory(),
                                        result.getGooglePlaceId()
                                )
                        )
                        .collect(Collectors.toList())
        );
    }

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
