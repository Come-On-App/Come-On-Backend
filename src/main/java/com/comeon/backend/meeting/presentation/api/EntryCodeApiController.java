package com.comeon.backend.meeting.presentation.api;

import com.comeon.backend.common.security.JwtPrincipal;
import com.comeon.backend.meeting.MemberRole;
import com.comeon.backend.meeting.command.application.MeetingFacade;
import com.comeon.backend.meeting.command.application.dto.MeetingCommandDto;
import com.comeon.backend.meeting.presentation.RequiredMemberRole;
import com.comeon.backend.meeting.query.dao.MeetingDao;
import com.comeon.backend.meeting.query.dao.dto.EntryCodeDetailsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/meetings/{meetingId}/entry-code")
public class EntryCodeApiController {

    private final MeetingFacade meetingFacade;
    private final MeetingDao meetingDao;

    @RequiredMemberRole
    @GetMapping
    public EntryCodeDetailsResponse entryCodeDetails(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @PathVariable Long meetingId
    ) {
        return meetingDao.findEntryCodeDetails(meetingId);
    }

    @RequiredMemberRole(MemberRole.HOST)
    @PostMapping
    public MeetingCommandDto.RenewEntryCodeResponse entryCodeRenew(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @PathVariable Long meetingId
    ) {
        MeetingCommandDto.RenewEntryCodeResponse response
                = meetingFacade.renewEntryCode(meetingId, jwtPrincipal.getUserId());
        return response;
    }
}
