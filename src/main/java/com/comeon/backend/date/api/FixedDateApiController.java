package com.comeon.backend.date.api;

import com.comeon.backend.common.config.interceptor.MemberRole;
import com.comeon.backend.common.config.interceptor.RequiredMemberRole;
import com.comeon.backend.date.api.dto.MeetingDateConfirmResponse;
import com.comeon.backend.date.command.application.confirm.DateConfirmFacade;
import com.comeon.backend.date.command.application.confirm.FixedDateAddRequest;
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

    @RequiredMemberRole(MemberRole.HOST)
    @PostMapping
    public MeetingDateConfirmResponse meetingDateConfirm(
            @PathVariable Long meetingId,
            @Validated @RequestBody FixedDateAddRequest request
    ) {
        dateConfirmFacade.confirmMeetingDate(meetingId, request);
        return new MeetingDateConfirmResponse();
    }
}
