package com.comeon.backend.meeting.presentation.api;

import com.comeon.backend.common.security.JwtPrincipal;
import com.comeon.backend.meeting.command.application.MeetingCommandService;
import com.comeon.backend.meeting.presentation.api.response.MeetingEntryCodeDetailResponse;
import com.comeon.backend.meeting.presentation.api.response.MeetingEntryCodeRenewResponse;
import com.comeon.backend.meeting.query.application.MeetingQueryService;
import com.comeon.backend.meeting.query.dto.MeetingEntryCodeDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/meetings/{meetingId}/entry-code")
public class MeetingEntryCodeController {

    private final MeetingCommandService meetingCommandService;
    private final MeetingQueryService meetingQueryService;

    @GetMapping
    public MeetingEntryCodeDetailResponse entryCodeDetails(@AuthenticationPrincipal JwtPrincipal jwtPrincipal,
                                                           @PathVariable Long meetingId) {
        MeetingEntryCodeDetails entryCodeDetails = meetingQueryService.findMeetingEntryCodeDetails(meetingId, jwtPrincipal.getUserId());
        return new MeetingEntryCodeDetailResponse(entryCodeDetails.getMeetingId(), entryCodeDetails.getEntryCode(), entryCodeDetails.getExpiration());
    }

    @PostMapping
    public MeetingEntryCodeRenewResponse entryCodeRenew(@AuthenticationPrincipal JwtPrincipal jwtPrincipal,
                                                        @PathVariable Long meetingId) {
        String entryCode = meetingCommandService.renewEntryCode(meetingId, jwtPrincipal.getUserId());
        return new MeetingEntryCodeRenewResponse(entryCode);
    }
}
