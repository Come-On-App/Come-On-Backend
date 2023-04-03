package com.comeon.backend.report.command.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportType {

    MEETING("모임 신고"),
    USER("유저 신고"),
    BUG("버그 신고"),
    SUGGEST("건의사항"),
    ;

    private final String description;
}
