package com.comeon.backend.meeting.presentation.api.meetingdate;

import com.comeon.backend.meeting.command.domain.MemberRole;
import com.comeon.backend.meeting.presentation.interceptor.RequiredMemberRole;
import com.comeon.backend.meeting.command.application.v1.CancelMeetingDateFacade;
import com.comeon.backend.meeting.command.application.v1.ConfirmMeetingDateFacade;
import com.comeon.backend.meeting.command.application.v1.dto.MeetingDateConfirmRequest;
import com.comeon.backend.meeting.query.dao.FixedDateDao;
import com.comeon.backend.meeting.query.dto.MeetingFixedDateSimple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/meetings/{meetingId}/date/confirm")
public class MeetingDateController {

    private final FixedDateDao fixedDateDao;
    private final ConfirmMeetingDateFacade confirmMeetingDateFacade;
    private final CancelMeetingDateFacade cancelMeetingDateFacade;

    @RequiredMemberRole(MemberRole.HOST)
    @PostMapping
    public MeetingDateConfirmResponse meetingDateConfirm(
            @PathVariable Long meetingId,
            @Validated @RequestBody MeetingDateConfirmRequest request
    ) {
        confirmMeetingDateFacade.confirmMeetingDate(meetingId, request);
        return new MeetingDateConfirmResponse();
    }

    @RequiredMemberRole(MemberRole.HOST)
    @DeleteMapping
    public MeetingDateCancelResponse meetingDateCancel(
            @PathVariable Long meetingId
    ) {
        cancelMeetingDateFacade.cancelMeetingDate(meetingId);
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
