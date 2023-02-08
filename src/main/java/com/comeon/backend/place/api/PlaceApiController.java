package com.comeon.backend.place.api;

import com.comeon.backend.config.web.member.RequiredMemberRole;
import com.comeon.backend.common.response.ListResponse;
import com.comeon.backend.config.security.JwtPrincipal;
import com.comeon.backend.place.api.dto.PlaceAddResponse;
import com.comeon.backend.place.command.application.MeetingPlaceFacade;
import com.comeon.backend.place.command.application.dto.PlaceAddRequest;
import com.comeon.backend.place.command.application.dto.PlaceModifyRequest;
import com.comeon.backend.place.query.PlaceDetails;
import com.comeon.backend.place.api.dto.PlaceModifyResponse;
import com.comeon.backend.place.api.dto.PlaceRemoveResponse;
import com.comeon.backend.place.query.MeetingPlaceDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/meetings/{meetingId}/places")
public class PlaceApiController {

    private final MeetingPlaceFacade meetingPlaceFacade;
    private final MeetingPlaceDao meetingPlaceDao;

    @RequiredMemberRole
    @GetMapping
    public ListResponse<PlaceDetails> placeList(
            @PathVariable Long meetingId
    ) {
        List<PlaceDetails> result = meetingPlaceDao.findPlacesByMeetingId(meetingId);
        return ListResponse.toListResponse(result);
    }

    @RequiredMemberRole
    @PostMapping
    public PlaceAddResponse placeAdd(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @PathVariable Long meetingId,
            @Validated @RequestBody PlaceAddRequest request
    ) {
        Long meetingPlaceId = meetingPlaceFacade.addPlace(jwtPrincipal.getUserId(), meetingId, request);
        return new PlaceAddResponse(meetingPlaceId);
    }

    @RequiredMemberRole
    @PutMapping("/{meetingPlaceId}")
    public PlaceModifyResponse placeModify(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @PathVariable Long meetingId,
            @PathVariable Long meetingPlaceId,
            @Validated @RequestBody PlaceModifyRequest request
    ) {
        meetingPlaceFacade.modifyPlace(jwtPrincipal.getUserId(), meetingId, meetingPlaceId, request);
        return new PlaceModifyResponse();
    }

    @RequiredMemberRole
    @DeleteMapping("/{meetingPlaceId}")
    public PlaceRemoveResponse placeRemove(
            @PathVariable Long meetingId,
            @PathVariable Long meetingPlaceId
    ) {
        meetingPlaceFacade.removePlace(meetingId, meetingPlaceId);
        return new PlaceRemoveResponse();
    }
}
