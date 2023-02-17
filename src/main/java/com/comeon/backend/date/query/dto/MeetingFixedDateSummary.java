package com.comeon.backend.date.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MeetingFixedDateSummary {

    private Long meetingId;
    private String meetingName;
    private FixedDateSimple fixedDate;
}
