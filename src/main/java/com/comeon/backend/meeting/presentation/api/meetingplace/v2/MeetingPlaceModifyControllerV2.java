package com.comeon.backend.meeting.presentation.api.meetingplace.v2;

import com.comeon.backend.config.security.JwtPrincipal;
import com.comeon.backend.meeting.command.application.v2.LockMeetingPlaceFacade;
import com.comeon.backend.meeting.command.application.v2.ModifyMeetingPlaceFacadeV2;
import com.comeon.backend.meeting.command.application.v2.UnlockMeetingPlaceFacade;
import com.comeon.backend.meeting.command.application.v2.dto.PlaceModifyV2Request;
import com.comeon.backend.meeting.presentation.interceptor.RequiredMemberRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/meetings/{meetingId}/places/{meetingPlaceId}")
public class MeetingPlaceModifyControllerV2 {

    private final LockMeetingPlaceFacade lockMeetingPlaceFacade;
    private final UnlockMeetingPlaceFacade unlockMeetingPlaceFacade;
    private final ModifyMeetingPlaceFacadeV2 modifyMeetingPlaceFacadeV2;

    @RequiredMemberRole
    @PostMapping("/lock")
    public MeetingPlaceLockResponse meetingPlaceLock(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @PathVariable Long meetingId,
            @PathVariable Long meetingPlaceId
    ) {
        lockMeetingPlaceFacade.lockMeetingPlace(jwtPrincipal.getUserId(), meetingId, meetingPlaceId);
        return new MeetingPlaceLockResponse();
    }

    @RequiredMemberRole
    @PostMapping("/unlock")
    public MeetingPlaceUnlockResponse meetingPlaceUnlock(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @PathVariable Long meetingId,
            @PathVariable Long meetingPlaceId
    ) {
        unlockMeetingPlaceFacade.unlockMeetingPlace(jwtPrincipal.getUserId(), meetingId, meetingPlaceId);
        return new MeetingPlaceUnlockResponse();
    }

    @RequiredMemberRole
    @PutMapping
    public PlaceModifyV2Response meetingPlaceModify(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @PathVariable Long meetingId,
            @PathVariable Long meetingPlaceId,
            @Validated @RequestBody PlaceModifyV2Request request
    ) {
        modifyMeetingPlaceFacadeV2.modifyMeetingPlace(
                jwtPrincipal.getUserId(),
                meetingId,
                meetingPlaceId,
                request
        );
        return new PlaceModifyV2Response();
    }
}
