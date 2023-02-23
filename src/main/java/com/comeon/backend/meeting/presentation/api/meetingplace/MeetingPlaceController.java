package com.comeon.backend.meeting.presentation.api.meetingplace;

import com.comeon.backend.common.response.ListResponse;
import com.comeon.backend.meeting.presentation.interceptor.RequiredMemberRole;
import com.comeon.backend.meeting.command.application.AddMeetingPlaceFacade;
import com.comeon.backend.meeting.command.application.ModifyMeetingPlaceFacade;
import com.comeon.backend.meeting.command.application.RemoveMeetingPlaceFacade;
import com.comeon.backend.meeting.command.application.dto.PlaceAddRequest;
import com.comeon.backend.meeting.command.application.dto.PlaceModifyRequest;
import com.comeon.backend.meeting.query.dao.MeetingPlaceDao;
import com.comeon.backend.meeting.query.dto.PlaceDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/meetings/{meetingId}/places")
public class MeetingPlaceController {

    private final MeetingPlaceDao meetingPlaceDao;
    private final AddMeetingPlaceFacade addMeetingPlaceFacade;
    private final ModifyMeetingPlaceFacade modifyMeetingPlaceFacade;
    private final RemoveMeetingPlaceFacade removeMeetingPlaceFacade;

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
    public PlaceAddResponse meetingPlaceAdd(
            @PathVariable Long meetingId,
            @Validated @RequestBody PlaceAddRequest request
    ) {
        Long meetingPlaceId = addMeetingPlaceFacade.addMeetingPlace(meetingId, request);
        return new PlaceAddResponse(meetingPlaceId);
    }

    @RequiredMemberRole
    @PutMapping("/{meetingPlaceId}")
    public PlaceModifyResponse meetingPlaceModify(
            @PathVariable Long meetingId,
            @PathVariable Long meetingPlaceId,
            @Validated @RequestBody PlaceModifyRequest request
    ) {
        modifyMeetingPlaceFacade.modifyMeetingPlace(meetingId, meetingPlaceId, request);
        return new PlaceModifyResponse();
    }

    @RequiredMemberRole
    @DeleteMapping("/{meetingPlaceId}")
    public PlaceRemoveResponse meetingPlaceRemove(
            @PathVariable Long meetingId,
            @PathVariable Long meetingPlaceId
    ) {
        removeMeetingPlaceFacade.removeMeetingPlace(meetingId, meetingPlaceId);
        return new PlaceRemoveResponse();
    }
}
