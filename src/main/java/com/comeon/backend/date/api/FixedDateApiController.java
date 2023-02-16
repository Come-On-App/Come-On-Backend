package com.comeon.backend.date.api;

import com.comeon.backend.config.web.member.MemberRole;
import com.comeon.backend.config.web.member.RequiredMemberRole;
import com.comeon.backend.date.api.dto.MeetingDateCancelResponse;
import com.comeon.backend.date.api.dto.MeetingDateConfirmResponse;
import com.comeon.backend.date.command.application.confirm.DateConfirmFacade;
import com.comeon.backend.date.command.application.confirm.FixedDateAddRequest;
import com.comeon.backend.date.query.dao.FixedDateDao;
import com.comeon.backend.date.query.dto.MeetingFixedDateSimple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/meetings/{meetingId}/date/confirm")
public class FixedDateApiController {

    private final DateConfirmFacade dateConfirmFacade;
    private final FixedDateDao fixedDateDao;

    @RequiredMemberRole(MemberRole.HOST)
    @PostMapping
    public MeetingDateConfirmResponse meetingDateConfirm(
            @PathVariable Long meetingId,
            @Validated @RequestBody FixedDateAddRequest request
    ) {
        dateConfirmFacade.confirmMeetingDate(meetingId, request);
        return new MeetingDateConfirmResponse();
    }

    @RequiredMemberRole(MemberRole.HOST)
    @DeleteMapping
    public MeetingDateCancelResponse meetingDateCancel(
            @PathVariable Long meetingId
    ) {
        dateConfirmFacade.cancelMeetingConfirmedDate(meetingId);
        return new MeetingDateCancelResponse();
    }

    @RequiredMemberRole
    @GetMapping
    public MeetingFixedDateSimple fixedDateSimple(
            @PathVariable Long meetingId
    ) {
        return fixedDateDao.findFixedDateSimple(meetingId);
    }
}
