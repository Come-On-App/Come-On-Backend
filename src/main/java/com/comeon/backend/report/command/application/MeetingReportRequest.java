package com.comeon.backend.report.command.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MeetingReportRequest {

    @NotNull
    private Long meetingId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private String reportImageUrl;
}
