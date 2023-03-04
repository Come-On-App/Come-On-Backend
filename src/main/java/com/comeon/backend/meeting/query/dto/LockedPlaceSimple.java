package com.comeon.backend.meeting.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LockedPlaceSimple {

    private Long meetingPlaceId;
    private Long lockingUserId;
}
