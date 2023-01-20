package com.comeon.backend.meeting.query.dao.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindMeetingSliceResult {

    private Long meetingId;
    private Long hostUserId;
    private String hostUserNickname;
    private String hostUserProfileImageUrl;
    private Integer memberCount;
    private String myMeetingRole;
    private String meetingName;
    private String calendarStartFrom;
    private String calendarEndTo;
    private String meetingImageUrl;
}
