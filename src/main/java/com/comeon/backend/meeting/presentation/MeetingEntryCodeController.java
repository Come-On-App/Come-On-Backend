package com.comeon.backend.meeting.presentation;

import com.comeon.backend.common.security.JwtPrincipal;
import com.comeon.backend.meeting.command.application.dto.EntryCodeDetails;
import com.comeon.backend.meeting.command.application.MeetingFacade;
import com.comeon.backend.meeting.presentation.response.EntryCodeDetailResponse;
import com.comeon.backend.meeting.presentation.response.EntryCodeRenewResponse;
import com.comeon.backend.meeting.query.application.MeetingQueryService;
import com.comeon.backend.meeting.query.application.dto.MeetingEntryCodeDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/meetings/{meetingId}/entry-code")
public class MeetingEntryCodeController {

    private final MeetingFacade meetingFacade;
    private final MeetingQueryService meetingQueryService;

    @GetMapping
    public EntryCodeDetailResponse entryCodeDetails(@AuthenticationPrincipal JwtPrincipal jwtPrincipal,
                                                    @PathVariable Long meetingId) {
        MeetingEntryCodeDetails entryCodeDetails = meetingQueryService.findMeetingEntryCodeDetails(meetingId, jwtPrincipal.getUserId());
        return new EntryCodeDetailResponse(entryCodeDetails.getMeetingId(), entryCodeDetails.getEntryCode(), entryCodeDetails.getExpiration());
    }

    @PostMapping
    public EntryCodeRenewResponse entryCodeRenew(@AuthenticationPrincipal JwtPrincipal jwtPrincipal,
                                                 @PathVariable Long meetingId) {
        EntryCodeDetails entryCodeDetails = meetingFacade.renewEntryCode(meetingId, jwtPrincipal.getUserId());
        return new EntryCodeRenewResponse(entryCodeDetails.getMeetingId(), entryCodeDetails.getEntryCode(), entryCodeDetails.getExpiration());
    }
}
