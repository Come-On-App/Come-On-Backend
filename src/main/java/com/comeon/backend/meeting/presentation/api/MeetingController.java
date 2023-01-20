package com.comeon.backend.meeting.presentation.api;

import com.comeon.backend.common.security.JwtPrincipal;
import com.comeon.backend.meeting.command.application.MeetingCommandService;
import com.comeon.backend.meeting.command.application.MeetingMemberCommandService;
import com.comeon.backend.meeting.command.application.MeetingMemberSummary;
import com.comeon.backend.meeting.presentation.api.request.MeetingAddRequest;
import com.comeon.backend.meeting.presentation.api.request.MeetingJoinRequest;
import com.comeon.backend.meeting.presentation.api.response.MeetingAddResponse;
import com.comeon.backend.meeting.presentation.api.response.MeetingJoinResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/meetings")
public class MeetingController {

    private final MeetingCommandService meetingCommandService;
    private final MeetingMemberCommandService meetingMemberCommandService;

    @PostMapping
    public MeetingAddResponse meetingAdd(@AuthenticationPrincipal JwtPrincipal jwtPrincipal,
                                         @Validated @RequestBody MeetingAddRequest request) {
        Long meetingId = meetingCommandService.addMeeting(
                jwtPrincipal.getUserId(),
                request.getMeetingName(),
                request.getMeetingImageUrl(),
                request.getCalendarStartFrom(),
                request.getCalendarEndTo()
        );

        return new MeetingAddResponse(meetingId);
    }

    @PostMapping("/join")
    public MeetingJoinResponse meetingJoin(@AuthenticationPrincipal JwtPrincipal jwtPrincipal,
                                           @Validated @RequestBody MeetingJoinRequest request) {
        MeetingMemberSummary meetingMemberSummary = meetingMemberCommandService.joinMeeting(jwtPrincipal.getUserId(), request.getEntryCode());
        return new MeetingJoinResponse(meetingMemberSummary.getMeetingId(), meetingMemberSummary.getMemberId(), meetingMemberSummary.getMemberRole());
    }


}
