package com.comeon.backend.meetingmember.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberListUpdateMessage {

    private Long meetingId;
}
