package com.comeon.backend.report.presentation;

import com.comeon.backend.config.security.JwtPrincipal;
import com.comeon.backend.report.command.application.MeetingReportFacade;
import com.comeon.backend.report.command.application.MeetingReportRequest;
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
@RequestMapping("/api/v1/report/meeting")
public class MeetingReportController {

    private final MeetingReportFacade meetingReportFacade;

    @PostMapping
    public MeetingReportResponse meetingReport(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @Validated @RequestBody MeetingReportRequest request
    ) {
        Long reportId = meetingReportFacade.saveMeetingReport(request, jwtPrincipal.getUserId());
        return new MeetingReportResponse(reportId);
    }
}
