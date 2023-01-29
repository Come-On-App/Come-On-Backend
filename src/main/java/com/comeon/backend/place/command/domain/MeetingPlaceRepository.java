package com.comeon.backend.place.command.domain;

import java.util.List;
import java.util.Optional;

public interface MeetingPlaceRepository {

    MeetingPlace save(MeetingPlace meetingPlace);
    List<MeetingPlace> findByMeetingId(Long meetingId);
    Optional<MeetingPlace> findByMeetingIdAndPlaceId(Long meetingId, Long placeId);
}
