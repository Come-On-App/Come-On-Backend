package com.comeon.backend.place.api;

import com.comeon.backend.common.config.interceptor.RequiredMemberRole;
import com.comeon.backend.common.response.ListResponse;
import com.comeon.backend.common.security.JwtPrincipal;
import com.comeon.backend.place.api.dto.PlaceAddResponse;
import com.comeon.backend.place.api.dto.PlaceModifyResponse;
import com.comeon.backend.place.api.dto.PlaceRemoveResponse;
import com.comeon.backend.place.command.application.MeetingPlaceFacade;
import com.comeon.backend.place.command.application.PlaceCommandDto;
import com.comeon.backend.place.query.dao.MeetingPlaceDao;
import com.comeon.backend.place.query.dto.PlaceListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ListResponse<PlaceListResponse> placeList(
            @PathVariable Long meetingId
    ) {
        List<PlaceListResponse> result = meetingPlaceDao.findPlacesByMeetingId(meetingId);
        return ListResponse.toListResponse(result);
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
            @PathVariable Long meetingId,
            @PathVariable Long meetingPlaceId
    ) {
        meetingPlaceFacade.removePlace(meetingId, meetingPlaceId);
        return new PlaceRemoveResponse();
    }
}
