package com.comeon.backend.meetingmember.api;

import com.comeon.backend.config.security.JwtPrincipal;
import com.comeon.backend.meetingmember.command.application.MeetingMemberFacade;
import com.comeon.backend.meetingmember.command.application.dto.MeetingJoinRequest;
import com.comeon.backend.meetingmember.command.application.dto.MeetingJoinResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/meetings/join")
public class MeetingJoinApiController {

    private final MeetingMemberFacade meetingMemberFacade;

    @PostMapping
    public MeetingJoinResponse meetingJoin(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @Validated @RequestBody MeetingJoinRequest request
    ) {
        return meetingMemberFacade.joinMeeting(jwtPrincipal.getUserId(), request);
    }
}
