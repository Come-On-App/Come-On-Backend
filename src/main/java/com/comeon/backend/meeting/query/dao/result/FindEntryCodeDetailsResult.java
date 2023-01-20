package com.comeon.backend.meeting.query.dao.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindEntryCodeDetailsResult {

    private Long meetingId;
    private String entryCode;
    private String lastModifiedDate;
    private Long userId;
}
