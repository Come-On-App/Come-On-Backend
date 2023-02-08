package com.comeon.backend.place.query;

import java.util.List;

public interface MeetingPlaceDao {

    List<PlaceDetails> findPlacesByMeetingId(Long meetingId);
}
