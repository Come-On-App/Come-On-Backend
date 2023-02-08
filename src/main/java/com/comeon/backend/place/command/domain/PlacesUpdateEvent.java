package com.comeon.backend.place.command.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PlacesUpdateEvent {

    private Long targetMeetingId;

    public static PlacesUpdateEvent create(Long meetingId) {
        return new PlacesUpdateEvent(meetingId);
    }
}
