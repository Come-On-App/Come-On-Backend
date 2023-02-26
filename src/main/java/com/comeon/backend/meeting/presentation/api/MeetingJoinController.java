package com.comeon.backend.meeting.presentation.api;

import com.comeon.backend.config.security.JwtPrincipal;
import com.comeon.backend.meeting.command.application.v1.JoinMeetingFacade;
import com.comeon.backend.meeting.command.application.v1.dto.MeetingJoinResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MeetingJoinController {

    private final JoinMeetingFacade joinMeetingFacade;

    @PostMapping("/api/v1/meetings/join")
    public MeetingJoinResponse meetingJoin(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @Validated @RequestBody MeetingJoinRequest request
    ) {
        return joinMeetingFacade.joinMeeting(jwtPrincipal.getUserId(), request.getEntryCode());
    }
}
