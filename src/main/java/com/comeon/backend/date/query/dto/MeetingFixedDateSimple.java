package com.comeon.backend.date.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MeetingFixedDateSimple {

    private Long meetingId;
    private FixedDateSimple fixedDate;
}
