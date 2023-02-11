package com.comeon.backend.place.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlaceListUpdateMessage {

    private Long targetMeetingId;
}
