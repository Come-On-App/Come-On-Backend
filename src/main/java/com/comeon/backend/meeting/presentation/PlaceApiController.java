package com.comeon.backend.meeting.presentation;

import com.comeon.backend.common.api.ListResponse;
import com.comeon.backend.common.security.JwtPrincipal;
import com.comeon.backend.meeting.command.application.MeetingPlaceFacade;
import com.comeon.backend.meeting.command.application.dto.PlaceCommandDto;
import com.comeon.backend.meeting.presentation.response.PlaceAddResponse;
import com.comeon.backend.meeting.query.dao.MeetingPlaceDao;
import com.comeon.backend.meeting.query.dao.dto.PlaceListResponse;
import com.comeon.backend.meeting.presentation.response.PlaceModifyResponse;
import com.comeon.backend.meeting.presentation.response.PlaceRemoveResponse;
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

    // TODO 요청 클라이언트가 모임에 속해있는지 권한 확인
    @GetMapping
    public ListResponse<PlaceListResponse> placeList(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @PathVariable Long meetingId
    ) {
        return ListResponse.toListResponse(meetingPlaceDao.findPlacesByMeetingId(meetingId));
    }

    @PostMapping
    public PlaceAddResponse placeAdd(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @PathVariable Long meetingId,
            @RequestBody PlaceCommandDto.AddRequest request
    ) {
        Long meetingPlaceId = meetingPlaceFacade.addPlace(jwtPrincipal.getUserId(), meetingId, request);
        return new PlaceAddResponse(meetingPlaceId);
    }

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
