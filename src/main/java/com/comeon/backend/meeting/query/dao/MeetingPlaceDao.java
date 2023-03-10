package com.comeon.backend.meeting.query.dao;

import com.comeon.backend.meeting.query.dto.LockedPlaceSimple;
import com.comeon.backend.meeting.query.dto.PlaceDetails;

import java.util.List;

public interface MeetingPlaceDao {

    List<PlaceDetails> findPlacesByMeetingId(Long meetingId);
    List<LockedPlaceSimple> findLockedPlaceByMeetingId(Long meetingId);
}
