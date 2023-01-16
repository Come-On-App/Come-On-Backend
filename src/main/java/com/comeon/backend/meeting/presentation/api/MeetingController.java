package com.comeon.backend.meeting.presentation.api;

import com.comeon.backend.common.security.JwtPrincipal;
import com.comeon.backend.meeting.application.MeetingService;
import com.comeon.backend.meeting.presentation.api.request.MeetingAddRequest;
import com.comeon.backend.meeting.presentation.api.response.MeetingAddResponse;
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
@RequestMapping("/api/v1/meetings")
public class MeetingController {

    private final MeetingService meetingService;

    @PostMapping
    public MeetingAddResponse meetingAdd(@AuthenticationPrincipal JwtPrincipal jwtPrincipal,
                                         @Validated @RequestBody MeetingAddRequest request) {
        Long meetingId = meetingService.addMeeting(
                jwtPrincipal.getUserId(),
                request.getMeetingName(),
                request.getMeetingImageUrl(),
                request.getCalendarStartFrom(),
                request.getCalendarEndTo()
        );

        return new MeetingAddResponse(meetingId);
    }
}
