package com.comeon.backend.report.command.application;

import com.comeon.backend.report.command.domain.MeetingReportService;
import com.comeon.backend.report.command.domain.Report;
import com.comeon.backend.report.command.domain.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MeetingReportFacade {

    private final ReportRepository reportRepository;
    private final MeetingReportService meetingReportService;

    public Long saveMeetingReport(MeetingReportRequest request, Long userId) {
        Report meetingReport = meetingReportService.createMeetingReport(
                userId,
                request.getTitle(),
                request.getContent(),
                request.getReportImageUrl(),
                request.getMeetingId()
        );

        return reportRepository.save(meetingReport).getId();
    }
}
