package com.comeon.backend.meeting.presentation.api;

import com.comeon.backend.common.api.ListResponse;
import com.comeon.backend.common.security.JwtPrincipal;
import com.comeon.backend.meeting.command.application.MeetingPlaceFacade;
import com.comeon.backend.meeting.command.application.dto.PlaceCommandDto;
import com.comeon.backend.meeting.presentation.RequiredMemberRole;
import com.comeon.backend.meeting.presentation.api.dto.PlaceAddResponse;
import com.comeon.backend.meeting.query.dao.MeetingPlaceDao;
import com.comeon.backend.meeting.query.dao.dto.PlaceListResponse;
import com.comeon.backend.meeting.presentation.api.dto.PlaceModifyResponse;
import com.comeon.backend.meeting.presentation.api.dto.PlaceRemoveResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/meetings/{meetingId}/places")
public class PlaceApiController {

    private final MeetingPlaceFacade meetingPlaceFacade;
    private final MeetingPlaceDao meetingPlaceDao;

    @RequiredMemberRole
    @GetMapping
    public ListResponse<PlaceListResponse> placeList(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @PathVariable Long meetingId
    ) {
        return ListResponse.toListResponse(meetingPlaceDao.findPlacesByMeetingId(meetingId));
    }

    @RequiredMemberRole
    @PostMapping
    public PlaceAddResponse placeAdd(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @PathVariable Long meetingId,
            @RequestBody PlaceCommandDto.AddRequest request
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
            @RequestBody PlaceCommandDto.ModifyRequest request
    ) {
        meetingPlaceFacade.modifyPlace(jwtPrincipal.getUserId(), meetingId, meetingPlaceId, request);
        return new PlaceModifyResponse();
    }

    @RequiredMemberRole
    @DeleteMapping("/{meetingPlaceId}")
    public PlaceRemoveResponse placeRemove(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @PathVariable Long meetingId,
            @PathVariable Long meetingPlaceId
    ) {
        meetingPlaceFacade.removePlace(meetingId, meetingPlaceId);
        return new PlaceRemoveResponse();
    }
}
