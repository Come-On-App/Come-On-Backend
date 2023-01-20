package com.comeon.backend.meeting.query.dao.param;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindEntryCodeDetailsParam {

    private Long meetingId;
    private Long userId;
}
