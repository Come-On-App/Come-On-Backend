package com.comeon.backend.meeting.presentation.api;

import com.comeon.backend.config.security.JwtPrincipal;
import com.comeon.backend.meeting.query.application.FixedDateQueryService;
import com.comeon.backend.meeting.query.dto.MeetingFixedDatesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/meetings/date/confirm")
public class FixedDateListApiController {

    private final FixedDateQueryService fixedDateQueryService;

    @GetMapping
    public MeetingFixedDatesResponse meetingConfirmDates(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @Validated @ModelAttribute MeetingConfirmDatesParam param
    ) {
        return fixedDateQueryService.findMeetingFixedDates(
                jwtPrincipal.getUserId(),
                param.getYear(),
                param.getMonth()
        );
    }
}
