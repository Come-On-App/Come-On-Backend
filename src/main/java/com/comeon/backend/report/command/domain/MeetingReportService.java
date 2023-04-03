package com.comeon.backend.report.command.domain;

import com.comeon.backend.common.utils.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeetingReportService {

    private final ObjectMapper objectMapper;
    private final MeetingDetailService meetingDetailService;

    public Report createMeetingReport(Long writerId, String title, String content, String reportImageUrl, Long meetingId) {
        MeetingDetail meetingDetail = meetingDetailService.findMeetingDetailBy(meetingId);
        String jsonMeetingDetail = JsonUtils.toJson(objectMapper, meetingDetail);

        return Report.createMeetingReport(writerId, title, content, reportImageUrl, jsonMeetingDetail);
    }
}
