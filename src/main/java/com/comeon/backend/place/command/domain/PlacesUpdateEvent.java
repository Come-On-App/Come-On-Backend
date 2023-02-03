package com.comeon.backend.place.command.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlacesUpdateEvent {

    private Long targetMeetingId;

    public static PlacesUpdateEvent create(Long meetingId) {
        return new PlacesUpdateEvent(meetingId);
    }
}
