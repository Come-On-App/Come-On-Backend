package com.comeon.backend.meeting.presentation.api.entrycode;

import com.comeon.backend.meeting.command.domain.MemberRole;
import com.comeon.backend.meeting.presentation.interceptor.RequiredMemberRole;
import com.comeon.backend.meeting.command.application.RenewEntryCodeFacade;
import com.comeon.backend.meeting.command.application.dto.EntryCodeRenewResponse;
import com.comeon.backend.meeting.query.dao.MeetingDao;
import com.comeon.backend.meeting.query.dto.EntryCodeDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/meetings/{meetingId}/entry-code")
public class EntryCodeController {

    private final MeetingDao meetingDao;
    private final RenewEntryCodeFacade renewEntryCodeFacade;

    @RequiredMemberRole(MemberRole.HOST)
    @PostMapping
    public EntryCodeRenewResponse entryCodeRenew(
            @PathVariable Long meetingId
    ) {
        return renewEntryCodeFacade.renewEntryCode(meetingId);
    }

    @RequiredMemberRole
    @GetMapping
    public EntryCodeDetails entryCodeDetails(
            @PathVariable Long meetingId
    ) {
        return meetingDao.findEntryCodeDetails(meetingId);
    }
}
