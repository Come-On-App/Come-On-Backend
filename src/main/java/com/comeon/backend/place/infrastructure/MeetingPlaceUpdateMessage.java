package com.comeon.backend.place.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MeetingPlaceUpdateMessage {

    private Long targetMeetingId;
}
