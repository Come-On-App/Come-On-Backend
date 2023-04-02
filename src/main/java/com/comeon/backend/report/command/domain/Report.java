package com.comeon.backend.report.command.domain;

import com.comeon.backend.common.model.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReportType type;

    private Long writerId;

    private String title;
    private String content;
    private String reportImageUrl;

    @Column(columnDefinition = "TEXT")
    private String reference;

    @Builder
    private Report(ReportType type, Long writerId, String title, String content, String reportImageUrl, String reference) {
        this.type = type;
        this.writerId = writerId;
        this.title = title;
        this.content = content;
        this.reportImageUrl = reportImageUrl;
        this.reference = reference;
    }

    public static Report createMeetingReport(Long writerId, String title, String content, String reportImageUrl, String jsonMeetingDetail) {
        return Report.builder()
                .type(ReportType.MEETING)
                .writerId(writerId)
                .title(title)
                .content(content)
                .reportImageUrl(reportImageUrl)
                .reference(jsonMeetingDetail)
                .build();
    }

    public Long getId() {
        return id;
    }
}
